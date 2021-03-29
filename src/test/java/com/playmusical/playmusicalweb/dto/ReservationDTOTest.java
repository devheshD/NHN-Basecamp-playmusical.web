package com.playmusical.playmusicalweb.dto;

import org.junit.jupiter.api.Test;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ReservationDTOTest {

    @Test
    void reservationDTOLombokTest() {
        ReservationDTO origin = random(ReservationDTO.class);

        ReservationDTO builder = ReservationDTO.builder()
                .reservationNo(origin.getReservationNo())
                .performanceNo(origin.getPerformanceNo())
                .reservationState(origin.getReservationState())
                .userId(origin.getUserId())
                .reservationDate(origin.getReservationDate())
                .build();

        ReservationDTO setter = new ReservationDTO();
        setter.setReservationNo(origin.getReservationNo());
        setter.setReservationDate(origin.getReservationDate());
        setter.setPerformanceNo(origin.getPerformanceNo());
        setter.setUserId(origin.getUserId());
        setter.setReservationState(origin.getReservationState());

        assertEquals(origin.toString(), builder.toString());
        assertEquals(origin.toString(), setter.toString());
    }

}