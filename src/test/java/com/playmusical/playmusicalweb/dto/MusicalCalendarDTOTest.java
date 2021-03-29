package com.playmusical.playmusicalweb.dto;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MusicalCalendarDTOTest {

    private MusicalCalendarDTO musicalCalendarDTO;
    private MusicalDTO musicalDTO;

    @BeforeEach
    void setUp() {
        musicalDTO = random(MusicalDTO.class);
        musicalCalendarDTO = MusicalCalendarDTO.builder().performanceNo(30L)
            .performanceData(LocalDateTime.of(2021, 2, 21, 12, 0, 0)).musicalDTO(musicalDTO)
            .cancelDate(LocalDateTime.of(2021, 2, 20, 12, 0, 0)).build();
    }

    @Test
    void getMusicalCalendarTest() {
        assertEquals(30L, musicalCalendarDTO.getPerformanceNo());
        assertEquals(LocalDateTime.of(2021, 2, 21, 12, 0, 0),
            musicalCalendarDTO.getPerformanceData());
        assertEquals(musicalDTO.getMusicalNo(), musicalCalendarDTO.getMusicalDTO().getMusicalNo());
        assertEquals(LocalDateTime.of(2021, 2, 20, 12, 0, 0), musicalCalendarDTO.getCancelDate());
    }

    @Test
    void setMusicalCalendarTest() {
        MusicalCalendarDTO setMuscicalCalendarDto = new MusicalCalendarDTO();
        setMuscicalCalendarDto.setMusicalDTO(musicalDTO);
        setMuscicalCalendarDto.setPerformanceData(LocalDateTime.of(2021, 2, 21, 12, 0, 0));
        setMuscicalCalendarDto.setPerformanceNo(31L);
        setMuscicalCalendarDto.setCancelDate(LocalDateTime.of(2021, 2, 20, 12, 0, 0));

        assertEquals(musicalDTO.getMusicalNo(),
            setMuscicalCalendarDto.getMusicalDTO().getMusicalNo());
        assertEquals(LocalDateTime.of(2021, 2, 21, 12, 0, 0),
            setMuscicalCalendarDto.getPerformanceData());
        assertEquals(31L, setMuscicalCalendarDto.getPerformanceNo());
        assertEquals(LocalDateTime.of(2021, 2, 20, 12, 0, 0),
            setMuscicalCalendarDto.getCancelDate());
    }
}
