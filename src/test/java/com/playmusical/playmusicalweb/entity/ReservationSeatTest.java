package com.playmusical.playmusicalweb.entity;

import org.junit.jupiter.api.Test;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ReservationSeatTest {

    @Test
    void reservationDTOLombokTest() {
        ReservationSeat origin = random(ReservationSeat.class);
        ReservationSeatId seatId = origin.getReservationSeatId();
        Reservation reservation = origin.getReservation();
        Seat seat = origin.getSeatNo();

        ReservationSeat builder = ReservationSeat.builder()
                .reservationSeatId(seatId)
                .reservation(reservation)
                .seatNo(seat)
                .build();

        ReservationSeat setter = new ReservationSeat();
        setter.setReservation(reservation);
        setter.setReservationSeatId(seatId);
        setter.setSeatNo(seat);

        assertEquals(origin.getReservationSeatId().getReservationNo(), builder.getReservationSeatId().getReservationNo());
        assertEquals(origin.getReservationSeatId().getReservationNo(), setter.getReservationSeatId().getReservationNo());
    }
}