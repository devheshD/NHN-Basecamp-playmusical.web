package com.playmusical.playmusicalweb.service;

import com.playmusical.playmusicalweb.dto.MusicalDTO;
import com.playmusical.playmusicalweb.dto.PageRequestDTO;
import com.playmusical.playmusicalweb.dto.PageResultDTO;
import com.playmusical.playmusicalweb.dto.ReservationDTO;
import com.playmusical.playmusicalweb.entity.Musical;
import com.playmusical.playmusicalweb.entity.Reservation;
import com.playmusical.playmusicalweb.repository.ReservationRepo;
import com.playmusical.playmusicalweb.util.Common;
import java.time.LocalDate;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Log4j2
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepo reservationRepo;

    @Override
    public PageResultDTO<Reservation, Reservation> getReservationInfoList(String userId,
        PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("reservationNo").descending());
        Page<Reservation> result = reservationRepo
            .findReservationByUserId(userId, pageable);
        return new PageResultDTO<>(result);

    }

    @Override
    public void cancelReservation(Long reservationNo) {
        Optional<Reservation> reservation = reservationRepo.findById(reservationNo);
        reservation.ifPresent(value -> reservationRepo.updateState(value.getReservationNo()));
    }

}
