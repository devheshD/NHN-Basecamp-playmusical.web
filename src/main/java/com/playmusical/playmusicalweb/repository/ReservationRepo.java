package com.playmusical.playmusicalweb.repository;

import com.playmusical.playmusicalweb.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, Long> {

    Page<Reservation> findReservationByUserId(String userId, Pageable pageable);

    List<Reservation> findReservationByUserIdOrderByReservationNoDesc(String userId);

    @Transactional
    @Modifying
    @Query("update Reservation r set r.reservationState=0 where r.reservationNo = :reservationNo")
    void updateState(Long reservationNo);
}