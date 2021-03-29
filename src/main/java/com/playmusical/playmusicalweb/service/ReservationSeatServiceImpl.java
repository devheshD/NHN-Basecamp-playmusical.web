package com.playmusical.playmusicalweb.service;

import com.playmusical.playmusicalweb.entity.ReservationSeat;
import com.playmusical.playmusicalweb.repository.ReservationSeatRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationSeatServiceImpl implements ReservationSeatService {

    private final ReservationSeatRepo reservationSeatRepo;

    @Override
    public List<ReservationSeat> findByReservationSeatId_ReservationNo(Long reservationNo) {
        return reservationSeatRepo.findByReservationSeatId_ReservationNo(reservationNo);
    }

    @Override
    public void deleteReservation(Long reservationNo, Long performanceNo) {
        reservationSeatRepo
            .deleteByReservation_ReservationNoAndReservation_MusicalCalendar_PerformanceNo(
                reservationNo, performanceNo);
    }

}
