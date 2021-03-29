package com.playmusical.playmusicalweb.service;


import com.playmusical.playmusicalweb.dto.SeatDTO;
import com.playmusical.playmusicalweb.entity.Seat;

import java.util.List;

public interface SeatService {

    SeatDTO findBySeatNo(String seatNo);

    List<SeatDTO> findSeatList(List<Integer> seatNoList);

    default SeatDTO entityToDto(Seat seat) {
        return SeatDTO.builder()
            .seatNo(seat.getSeatNo())
            .seatPrice(seat.getSeatPrice())
            .seatRank(seat.getSeatRank())
            .build();
    }
}
