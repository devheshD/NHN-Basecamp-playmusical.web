package com.playmusical.playmusicalweb.dto;

import org.junit.jupiter.api.Test;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OccupyDTOTest {

    @Test
    void occupyDTOLombokTest() {
        OccupyDTO origin = random(OccupyDTO.class);

        OccupyDTO builder = OccupyDTO.builder()
                .userId(origin.getUserId())
                .performanceNo(origin.getPerformanceNo())
                .seatNo(origin.getSeatNo())
                .build();

        OccupyDTO setter = new OccupyDTO();
        setter.setUserId(origin.getUserId());
        setter.setSeatNo(origin.getSeatNo());
        setter.setPerformanceNo(origin.getPerformanceNo());

        assertEquals(origin.toString(), builder.toString());
        assertEquals(origin.toString(), setter.toString());
        assertEquals(origin.getUserId(), builder.getUserId());
        assertEquals(origin.getPerformanceNo(), builder.getPerformanceNo());
        assertEquals(origin.getSeatNo(), builder.getSeatNo());
    }

}