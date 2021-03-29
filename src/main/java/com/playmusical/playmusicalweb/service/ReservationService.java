package com.playmusical.playmusicalweb.service;


import com.playmusical.playmusicalweb.dto.PageRequestDTO;
import com.playmusical.playmusicalweb.dto.PageResultDTO;
import com.playmusical.playmusicalweb.dto.ReservationDTO;
import com.playmusical.playmusicalweb.entity.Reservation;
import java.util.List;


public interface ReservationService {

    PageResultDTO<Reservation, Reservation> getReservationInfoList(String userId, PageRequestDTO pageRequestDTO);

    void cancelReservation(Long reservationNo);

}
