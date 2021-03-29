package com.playmusical.playmusicalweb.entity;

import org.junit.jupiter.api.Test;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ReservationTest {

    @Test
    void reservationDTOLombokTest() {
        Reservation origin = random(Reservation.class);

        Reservation builder = Reservation.builder()
                .reservationNo(origin.getReservationNo())
                .musicalCalendar(origin.getMusicalCalendar())
                .reservationState(origin.getReservationState())
                .userId(origin.getUserId())
                .reservationDate(origin.getReservationDate())
                .build();

        Reservation setter = new Reservation();
        setter.setReservationNo(origin.getReservationNo());
        setter.setReservationDate(origin.getReservationDate());
        setter.setMusicalCalendar(origin.getMusicalCalendar());
        setter.setUserId(origin.getUserId());
        setter.setReservationState(origin.getReservationState());

        assertEquals(origin.getReservationNo(), builder.getReservationNo());
        assertEquals(origin.getReservationNo(), setter.getReservationNo());
    }
}