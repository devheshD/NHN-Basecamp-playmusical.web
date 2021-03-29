package com.playmusical.playmusicalweb.repository;

import com.playmusical.playmusicalweb.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepo extends JpaRepository<Seat, Integer> {

    Seat findBySeatNo(int seatNo);
}
