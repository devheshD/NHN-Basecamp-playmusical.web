package com.playmusical.playmusicalweb.dto;

import org.junit.jupiter.api.Test;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SeatDTOTest {

    @Test
    void seatDTOLombokTest() {
        SeatDTO origin = random(SeatDTO.class);

        SeatDTO builder = SeatDTO.builder()
                .seatNo(origin.getSeatNo())
                .seatPrice(origin.getSeatPrice())
                .seatRank(origin.getSeatRank())
                .build();

        SeatDTO setter = new SeatDTO();
        setter.setSeatNo(origin.getSeatNo());
        setter.setSeatPrice(origin.getSeatPrice());
        setter.setSeatRank(origin.getSeatRank());

        assertEquals(origin.getSeatNo(), builder.getSeatNo());
        assertEquals(origin.getSeatNo(), setter.getSeatNo());
    }

}