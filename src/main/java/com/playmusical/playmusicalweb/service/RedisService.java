package com.playmusical.playmusicalweb.service;

import com.playmusical.playmusicalweb.dto.OccupyDTO;
import com.playmusical.playmusicalweb.dto.SeatDTO;
import com.playmusical.playmusicalweb.entity.Reservation;
import com.playmusical.playmusicalweb.entity.ReservationSeat;
import com.playmusical.playmusicalweb.entity.ReservationSeatId;
import com.playmusical.playmusicalweb.entity.Seat;
import com.playmusical.playmusicalweb.repository.MusicalCalendarRepo;
import com.playmusical.playmusicalweb.repository.ReservationRepo;
import com.playmusical.playmusicalweb.repository.ReservationSeatRepo;
import com.playmusical.playmusicalweb.util.Common;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ReservationRepo reservationRepo;
    private final ReservationSeatRepo reservationSeatRepo;
    private final MusicalCalendarRepo musicalCalendarRepo;

    private OccupyDTO buildOccupyDto(String userId, String performanceNo, String seatNo) {
        return OccupyDTO.builder().userId(userId).seatNo(Integer.parseInt(seatNo))
            .performanceNo(Long.parseLong(performanceNo)).build();
    }

    public boolean insert(String id, String performanceNo, String seatNo) {
        OccupyDTO occupyDto = buildOccupyDto(id, performanceNo, seatNo);
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        String key = occupyDto.getSeatNo() + ":" + occupyDto.getPerformanceNo();
        String value = occupyDto.getUserId();
        Boolean hasKey = redisTemplate.hasKey(key);
        if (Boolean.TRUE.equals(hasKey)) {
            return false;
        } else {
            valueOperations.set(key, value, Common.PRETIME_FIRSTRESEVATION, TimeUnit.SECONDS);
            return true;
        }
    }

    public boolean delete(String id, String performanceNo, String seatNo) {
        OccupyDTO occupyDto = buildOccupyDto(id, performanceNo, seatNo);
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        String key = occupyDto.getSeatNo() + ":" + occupyDto.getPerformanceNo();
        String value = occupyDto.getUserId();
        if (Objects.equals(valueOperations.get(key), value)) {
            redisTemplate.delete(key);
            return true;
        } else {
            return false;
        }
    }

    public boolean reserve(List<OccupyDTO> reserveInfo) {
        if (reserveInfo == null) {
            return false;
        }
        if (!reserveInfo.isEmpty() && checkAll(reserveInfo)) {
            return true;
        } else {
            deleteAll(reserveInfo);
            return false;
        }
    }

    public boolean checkAll(List<OccupyDTO> occupyDTOList) {
        if (occupyDTOList != null && !occupyDTOList.isEmpty()) {
            ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
            ListOperations<String, Object> list = redisTemplate.opsForList();
            String value = occupyDTOList.get(0).getUserId();
            if (value != null) {
                redisTemplate.delete(value);
                for (OccupyDTO occupyDto : occupyDTOList) {
                    String key = occupyDto.getSeatNo() + ":" + occupyDto.getPerformanceNo();
                    if (!Objects.equals(valueOperations.get(key), value)) {
                        redisTemplate.delete(value);
                        return false;
                    }
                    valueOperations
                        .set(key, value, Common.PRETIME_FIRSTRESEVATION, TimeUnit.SECONDS);
                    list.leftPush(value, key);
                }
                redisTemplate.expire(value, Common.PRETIME_FIRSTRESEVATION, TimeUnit.SECONDS);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean finishReserve(List<OccupyDTO> reserveInfo) {
        if (checkAll(reserveInfo)) {
            saveReservation(reserveInfo);
            return true;
        } else {
            return false;
        }
    }

    private void saveReservation(List<OccupyDTO> reserveInfo) {
        long performanceNo = reserveInfo.get(0).getPerformanceNo();
        String userId = reserveInfo.get(0).getUserId();
        Reservation reservation = Reservation.builder()
            .musicalCalendar(musicalCalendarRepo.findByPerformanceNo(performanceNo))
            .reservationDate(LocalDateTime.now()).userId(userId)
            .reservationState(Common.POSSIBLE_CANCLE.YES.ordinal()).build();
        for (OccupyDTO occupyDto : reserveInfo) {
            int seatNo = occupyDto.getSeatNo();
            reservationRepo.save(reservation);
            reservationSeatRepo.save(ReservationSeat.builder().reservationSeatId(
                ReservationSeatId.builder().reservationNo(reservation.getReservationNo())
                    .seatNo(seatNo).performanceNo(performanceNo).build()).reservation(reservation)
                .seatNo(Seat.builder().seatNo(seatNo).build()).build());
        }
    }

    public void deleteAll(List<OccupyDTO> occupyDTOList) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        String value = null;
        for (OccupyDTO occupyDto : occupyDTOList) {
            String key = occupyDto.getSeatNo() + ":" + occupyDto.getPerformanceNo();
            value = occupyDto.getUserId();
            if (Objects.equals(valueOperations.get(key), value)) {
                redisTemplate.delete(key);
            }
        }
        if (value != null) {
            redisTemplate.delete(value);
        }
    }

    public List<SeatDTO> getAll(String performanceNo) {
        List<SeatDTO> seatDTOList = new ArrayList<>();
        for (ReservationSeat seat : reservationSeatRepo
            .findAllByReservationSeatId_PerformanceNoOrderByReservationSeatId(
                Long.parseLong(performanceNo))) {
            SeatDTO seatDto = SeatDTO.builder().seatNo(seat.getSeatNo().getSeatNo())
                .seatPrice(seat.getSeatNo().getSeatPrice()).seatRank(seat.getSeatNo().getSeatRank())
                .build();
            seatDTOList.add(seatDto);
        }
        Set<String> listOccupySeat = redisTemplate.keys("*:" + performanceNo);
        if (Objects.requireNonNull(listOccupySeat).isEmpty()) {
            return seatDTOList;
        }
        for (String seat : listOccupySeat) {
            SeatDTO seatDto = SeatDTO.builder().seatNo(Integer.parseInt(seat.split(":")[0]))
                .build();
            seatDTOList.add(seatDto);
        }
        return seatDTOList;
    }

    public List<Integer> seatNoList(String id, String performanceNo) {
        List<Integer> seatNoList = new ArrayList<>();
        List<Object> list = redisTemplate.opsForList().range(id, 0, -1);
        for (Object ob : Objects.requireNonNull(list)) {
            String[] s = ob.toString().split(":");
            if (s[1].equals(performanceNo)) {
                seatNoList.add(Integer.valueOf(s[0]));
            }
        }
        return seatNoList;
    }
}
