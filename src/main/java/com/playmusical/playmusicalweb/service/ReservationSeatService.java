package com.playmusical.playmusicalweb.service;

import com.playmusical.playmusicalweb.entity.ReservationSeat;

import java.util.List;


public interface ReservationSeatService {

    List<ReservationSeat> findByReservationSeatId_ReservationNo(Long reservationNo);

    void deleteReservation(Long reservationNo, Long performanceNo);
}
