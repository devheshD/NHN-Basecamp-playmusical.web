package com.playmusical.playmusicalweb.service;

import com.playmusical.playmusicalweb.dto.SeatDTO;
import com.playmusical.playmusicalweb.entity.Seat;
import com.playmusical.playmusicalweb.repository.SeatRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepo seatRepo;

    @Override
    public SeatDTO findBySeatNo(String seatNo) {
        Seat seat = seatRepo.findBySeatNo(Integer.parseInt(seatNo));
        return entityToDto(seat);
    }

    @Override
    public List<SeatDTO> findSeatList(List<Integer> seatNoList) {
        List<SeatDTO> reserveList = new ArrayList<>();
        for (int seatNo : seatNoList) {
            reserveList.add(findBySeatNo(String.valueOf(seatNo)));
        }
        return reserveList;
    }
}
