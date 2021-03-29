package com.playmusical.playmusicalweb.service;

import com.playmusical.playmusicalweb.dto.*;
import com.playmusical.playmusicalweb.entity.Musical;
import com.playmusical.playmusicalweb.entity.MusicalCalendar;
import com.playmusical.playmusicalweb.repository.MusicalCalendarRepo;
import com.playmusical.playmusicalweb.repository.MusicalRepo;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class MusicalServiceImpl implements MusicalService {

    private static final Logger logger = LoggerFactory.getLogger(MusicalServiceImpl.class);
    private final MusicalRepo musicalRepo;

    private final MusicalCalendarRepo musicalCalendarRepo;

    @Override
    public List<MusicalDTO> musicalListAll() {
        List<MusicalDTO> list = new ArrayList<>();
        LocalDate curDate = LocalDate.now();
        PageRequest request = PageRequest.of(0, 8, Sort.by(Sort.Direction.ASC, "endDate"));
        musicalRepo.findByEndDateAfterOrderByEndDate(curDate, request).forEach(musical -> {
            LocalDate transDate = musical.getEndDate();
            if (curDate.compareTo(transDate) <= 0) {
                list.add(musicalToDTO(musical));
            }
        });
        return list;
    }

    @Override
    public PageResultDTO<MusicalDTO, Musical> musicalTitleList(String title,
        PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("endDate").descending());
        Page<Musical> result = musicalRepo
            .findByMusicalTitleContainingAndEndDateAfter(title, LocalDate.now(), pageable);
        Function<Musical, MusicalDTO> fn = (this::musicalTitleListToDTO);

        return new PageResultDTO<>(result, fn);

    }

    @Override
    public int musicalTitleTotalSize(String title) {
        return musicalRepo.findByMusicalTitleContainingAndEndDateAfter(title, LocalDate.now())
            .size();
    }

    @Override
    public MusicalDTO musicalDetail(Long musicalNo) {
        Optional<Musical> optional = musicalRepo.findById(musicalNo);

        return optional.map(this::musicalToDTO).orElse(null);
    }

    @Override
    public Long performanceNo(String musicalNo, LocalDate performanceDate) {
        long no = Long.parseLong(musicalNo);
        LocalDateTime startDate = performanceDate.atStartOfDay();
        LocalDateTime endDate = performanceDate.atStartOfDay().plusDays(1);
        return musicalCalendarRepo
            .findByMusical_MusicalNoAndPerformanceDateGreaterThanEqualAndPerformanceDateLessThan(no,
                startDate, endDate).getPerformanceNo();
    }

    @Transactional
    @Override
    public Long register(MusicalDTO musicalDTO) {
        Map<String, Object> entityMap = dtoToEntity(musicalDTO);
        Musical musical = (Musical) entityMap.get("musical");

        musicalRepo.save(musical);

        insertMusical(musical);

        return musical.getMusicalNo();
    }

    public void insertMusical(Musical musical) {
        LocalDateTime startDateTime = LocalDateTime
            .of(musical.getStartDate(), musical.getStartTime());
        LocalDateTime endDateTime = LocalDateTime
            .of(musical.getEndDate().plusDays(1), musical.getStartTime());

        do {
            LocalDateTime curDate = startDateTime;
            LocalDateTime beforeDate = curDate.minusDays(1);
            MusicalCalendar musicalCalendar = MusicalCalendar.builder().musical(musical)
                .performanceDate(curDate).cancelDate(beforeDate).build();
            musicalCalendarRepo.save(musicalCalendar);
            startDateTime = startDateTime.plusDays(1);

        } while (startDateTime.isBefore(endDateTime));


    }

    @Override
    public Map<String, Object> findByPerformanceNo(Long performanceNo) {
        MusicalCalendar musicalCalendar = musicalCalendarRepo.findByPerformanceNo(performanceNo);
        Musical musical = musicalCalendar.getMusical();
        MusicalDTO musicalDTO = musicalToDTO(musical);
        MusicalCalendarDTO musicalCalendarDTO = musicalCalendarToDTO(musicalCalendar);

        Map<String, Object> map = new HashMap<>();
        map.put("MusicalDto", musicalDTO);
        map.put("MusicalCalenderDto", musicalCalendarDTO);

        return map;
    }


    @Override
    public List<ReservationDTO> reservationDTOList_CLI(String userId) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<ReservationDTO>> response = restTemplate
            .exchange("http://playmusical.nhn.com:8080/api/mypage/" + userId, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ReservationDTO>>() {
                    @Override
                    public Type getType() {
                        return super.getType();
                    }
                });

        return response.getBody();
    }

    @Override
    public Map<String, Object> reservationSeatList_CLI(String userId, Long reservationNo) {

        List<ReservationDTO> reservationDTOList = reservationDTOList_CLI(userId);
        Map<String, Object> map = new HashMap<>();

        for (ReservationDTO reservationDTO : reservationDTOList) {
            if (reservationNo.equals(reservationDTO.getReservationNo())) {
                Long performanceNo = reservationDTO.getPerformanceNo();
                map.put("MusicalInfo", (findByPerformanceNo(performanceNo)));
                break;
            }
        }

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate
            .exchange("http://playmusical.nhn.com:8081/api/mypage/reservation2/" + reservationNo,
                HttpMethod.GET, null, String.class);
        map.put("SeatList", response.getBody());

        return map;
    }

}

