package com.playmusical.playmusicalweb.dto;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MusicalDTOTest {

    private MusicalDTO musicalDto;

    @BeforeEach
    void setUp() {
        List<MusicalCalendarDTO> list = new ArrayList<>();
        list.add(random(MusicalCalendarDTO.class));
        musicalDto = MusicalDTO.builder().musicalNo(1L).musicalTitle("이은결 마술쇼")
            .startDate(LocalDate.of(2021, 2, 21)).endDate(LocalDate.of(2021, 2, 28))
            .location("상암월드컵경기장").startTime(LocalTime.of(14, 0, 0)).endTime(LocalTime.of(16, 0, 0))
            .posterImg("이은결.jpg_poster_bfd38a83-2f98-408d-8d9e-cc4455b09540")
            .bannerImg("이은결배너.jpg_banner_bfd38a83-2f98-408d-8d9e-cc4455b09540")
            .musicalCalendarDTOList(list).build();
    }

    @Test
    void getMusicalDtoTest() {
        assertEquals(1L, musicalDto.getMusicalNo());
        assertEquals("이은결 마술쇼", musicalDto.getMusicalTitle());
        assertEquals(LocalDate.of(2021, 2, 21), musicalDto.getStartDate());
        assertEquals(LocalDate.of(2021, 2, 28), musicalDto.getEndDate());
        assertEquals("상암월드컵경기장", musicalDto.getLocation());
        assertEquals(LocalTime.of(14, 0, 0), musicalDto.getStartTime());
        assertEquals(LocalTime.of(16, 0, 0), musicalDto.getEndTime());
        assertEquals("이은결.jpg_poster_bfd38a83-2f98-408d-8d9e-cc4455b09540",
            musicalDto.getPosterImg());
        assertEquals("이은결배너.jpg_banner_bfd38a83-2f98-408d-8d9e-cc4455b09540",
            musicalDto.getBannerImg());
        assertEquals(1, musicalDto.getMusicalCalendarDTOList().size());
    }

    @Test
    void setMusicalDtoTest() {
        MusicalDTO setMuscicalDto = new MusicalDTO();
        setMuscicalDto.setMusicalNo(3L);
        setMuscicalDto.setMusicalTitle("이은결 마술");
        setMuscicalDto.setStartDate(LocalDate.of(2021, 2, 21));
        setMuscicalDto.setEndDate(LocalDate.of(2021, 2, 28));
        setMuscicalDto.setLocation("상암월드컵경기장");
        setMuscicalDto.setStartTime(LocalTime.of(14, 0, 0));
        setMuscicalDto.setEndTime(LocalTime.of(16, 0, 0));
        setMuscicalDto.setPosterImg("이은결.jpg_poster_bfd38a83-2f98-408d-8d9e-cc4455b09540");
        setMuscicalDto.setBannerImg("이은결배너.jpg_banner_bfd38a83-2f98-408d-8d9e-cc4455b09540");
        List<MusicalCalendarDTO> mock = new ArrayList<>();
        mock.add(random(MusicalCalendarDTO.class));
        setMuscicalDto.setMusicalCalendarDTOList(mock);
        assertEquals(3L, setMuscicalDto.getMusicalNo());
        assertEquals("이은결 마술", setMuscicalDto.getMusicalTitle());
        assertEquals(LocalDate.of(2021, 2, 21), setMuscicalDto.getStartDate());
        assertEquals(LocalDate.of(2021, 2, 28), setMuscicalDto.getEndDate());
        assertEquals("상암월드컵경기장", setMuscicalDto.getLocation());
        assertEquals(LocalTime.of(14, 0, 0), setMuscicalDto.getStartTime());
        assertEquals(LocalTime.of(16, 0, 0), setMuscicalDto.getEndTime());
        assertEquals("이은결.jpg_poster_bfd38a83-2f98-408d-8d9e-cc4455b09540",
            setMuscicalDto.getPosterImg());
        assertEquals("이은결배너.jpg_banner_bfd38a83-2f98-408d-8d9e-cc4455b09540",
            setMuscicalDto.getBannerImg());
        assertEquals(1, setMuscicalDto.getMusicalCalendarDTOList().size());
    }
}
