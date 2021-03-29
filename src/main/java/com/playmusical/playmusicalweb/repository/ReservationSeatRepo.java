package com.playmusical.playmusicalweb.repository;

import com.playmusical.playmusicalweb.entity.ReservationSeat;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface ReservationSeatRepo extends JpaRepository<ReservationSeat, Long> {

    List<ReservationSeat> findAllByReservationSeatId_PerformanceNoOrderByReservationSeatId(
        @Param("performanceNo") Long performanceNo);

    List<ReservationSeat> findByReservationSeatId_ReservationNo(Long reservationNo);

    @Transactional
    void deleteByReservation_ReservationNoAndReservation_MusicalCalendar_PerformanceNo(
        Long reservationNo, Long performanceNo);

}
