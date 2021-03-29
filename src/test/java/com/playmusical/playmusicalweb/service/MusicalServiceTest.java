package com.playmusical.playmusicalweb.service;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static io.github.benas.randombeans.api.EnhancedRandom.randomListOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.playmusical.playmusicalweb.dto.MusicalCalendarDTO;
import com.playmusical.playmusicalweb.dto.MusicalDTO;
import com.playmusical.playmusicalweb.dto.PageRequestDTO;
import com.playmusical.playmusicalweb.entity.Musical;
import com.playmusical.playmusicalweb.entity.MusicalCalendar;
import com.playmusical.playmusicalweb.repository.MusicalCalendarRepo;
import com.playmusical.playmusicalweb.repository.MusicalRepo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

@ExtendWith(MockitoExtension.class)
class MusicalServiceTest {


    @Mock
    private MusicalRepo musicalRepo;
    @Mock
    private MusicalCalendarRepo musicalCalendarRepo;
    @InjectMocks
    private MusicalServiceImpl musicalService;

    private MusicalDTO musicalDTO;
    private MusicalCalendar musicalCalendar;
    private Musical musical;

    @BeforeEach
    void SetUp() {
        musical = mock(Musical.class);
        musicalDTO = mock(MusicalDTO.class);
        musicalCalendar = mock(MusicalCalendar.class);
    }

    @Test
    void musicaltoDtoTest() {
        assertEquals(this.musicalService.musicalToDTO(musical).getMusicalNo(),
            musicalDTO.getMusicalNo());
    }

    @Test
    void entitiesToDtoTest() {
        Musical mockMusical = random(Musical.class);
        List<MusicalCalendar> mockList = randomListOf(5, MusicalCalendar.class);
        assertEquals(mockMusical.getMusicalNo(),
            this.musicalService.entitiesToDTO(mockMusical, mockList).getMusicalNo());
    }

    @Test
    void dtoToEntityTest() {
        MusicalDTO mock = random(MusicalDTO.class);
        mock.setMusicalCalendarDTOList(randomListOf(5, MusicalCalendarDTO.class));
        assertNotNull(this.musicalService.dtoToEntity(mock).get("musical"));
        assertNotNull(this.musicalService.dtoToEntity(mock).get("musicalCalendarList"));
    }

    @Test
    void findByPerformanceNoTest() {
        when(this.musicalCalendarRepo.findByPerformanceNo(musical.getMusicalNo()))
            .thenReturn(musicalCalendar);
        when(musicalCalendar.getMusical()).thenReturn(musical);
        assertNotNull(musicalService.findByPerformanceNo(musicalCalendar.getPerformanceNo()));
    }

    @Test
    void musicalListAllTest() {
        Musical mock = new Musical(1L, "title", LocalDate.of(2020, 2, 2), LocalDate.of(2022, 2, 20),
            "test", LocalTime.of(1, 1, 1), LocalTime.of(3, 3, 3), "test", "test",
            new ArrayList<>());
        PageRequest request = PageRequest.of(0, 8, Sort.by(Sort.Direction.ASC, "endDate"));
        List<Musical> list = new ArrayList<>();
        list.add(mock);
        Page<Musical> mockPage = new PageImpl<Musical>(list, request, list.size());
        when(this.musicalRepo.findAll(request)).thenReturn(mockPage);
        assertEquals(mock.getMusicalNo(),
            this.musicalService.musicalListAll().get(0).getMusicalNo());
    }

    @Test
    void getMusicalTitleListTest() {
        Musical mock = new Musical(1L, "title", LocalDate.of(2020, 2, 2), LocalDate.of(2022, 2, 20),
            "test", LocalTime.of(1, 1, 1), LocalTime.of(3, 3, 3), "test", "test",
            new ArrayList<>());
        PageRequest request = PageRequest.of(0, 8, Sort.by(Sort.Direction.ASC, "endDate"));
        List<Musical> list = new ArrayList<>();
        list.add(mock);
        Page<Musical> mockPage = new PageImpl<>(list, request, list.size());
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("end_date").descending());
        when(this.musicalRepo
            .findByMusicalTitleContainingAndEndDateAfter("title", LocalDate.now(), pageable))
            .thenReturn(mockPage);
        assertEquals(1L,
            this.musicalService.musicalTitleList("title", pageRequestDTO).getDtoList().get(0)
                .getMusicalNo());
    }

    @Test
    void getMusicalTitleTotalSizeTest() {
        Musical mock = new Musical(1L, "title", LocalDate.of(2020, 2, 2), LocalDate.of(2022, 2, 20),
            "test", LocalTime.of(1, 1, 1), LocalTime.of(3, 3, 3), "test", "test",
            new ArrayList<>());
        List<Musical> list = new ArrayList<>();
        list.add(mock);
        when(this.musicalRepo.findByMusicalTitleContainingAndEndDateAfter("title", LocalDate.now()))
            .thenReturn(list);
        assertEquals(1, this.musicalService.musicalTitleTotalSize("title"));
    }

    @Test
    void getMusicalDetailTest() {
        when(this.musicalRepo.findById(anyLong())).thenReturn(Optional.empty());
        assertNull(this.musicalService.musicalDetail(anyLong()));

        when(this.musicalRepo.findById(anyLong())).thenReturn(Optional.of(musical));
        assertNotNull(this.musicalService.musicalDetail(anyLong()));
    }

    @Test
    void getPerformanceNoTest() {
        MusicalCalendar mock = random(MusicalCalendar.class);
        when(this.musicalCalendarRepo
            .findByMusical_MusicalNoAndPerformanceDateGreaterThanEqualAndPerformanceDateLessThan(1L,
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), LocalDateTime.of(2020, 1, 2, 0, 0, 0)))
            .thenReturn(mock);
        assertEquals(mock.getPerformanceNo(),
            this.musicalService.performanceNo("1", LocalDate.of(2020, 1, 1)));
    }

    @Test
    void insertMusicalTest() {
        MusicalDTO mockDto = random(MusicalDTO.class);
        Musical mock = (Musical) this.musicalService.dtoToEntity(mockDto).get("musical");
        assertEquals(mock.getMusicalNo(), this.musicalService.register(mockDto));
    }


}
