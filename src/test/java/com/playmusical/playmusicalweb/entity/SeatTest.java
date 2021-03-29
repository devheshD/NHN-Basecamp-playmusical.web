package com.playmusical.playmusicalweb.entity;

import org.junit.jupiter.api.Test;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SeatTest {
    @Test
    void seatDTOLombokTest() {
        Seat origin = random(Seat.class);

        Seat builder = Seat.builder()
                .seatNo(origin.getSeatNo())
                .seatPrice(origin.getSeatPrice())
                .seatRank(origin.getSeatRank())
                .build();

        Seat setter = new Seat();
        setter.setSeatNo(origin.getSeatNo());
        setter.setSeatPrice(origin.getSeatPrice());
        setter.setSeatRank(origin.getSeatRank());

        assertEquals(origin.getSeatNo(), builder.getSeatNo());
        assertEquals(origin.getSeatNo(), setter.getSeatNo());
    }
}