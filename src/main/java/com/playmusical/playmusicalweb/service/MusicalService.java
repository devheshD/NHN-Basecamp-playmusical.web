package com.playmusical.playmusicalweb.service;

import com.playmusical.playmusicalweb.dto.MusicalCalendarDTO;
import com.playmusical.playmusicalweb.dto.MusicalDTO;
import com.playmusical.playmusicalweb.dto.PageRequestDTO;
import com.playmusical.playmusicalweb.dto.PageResultDTO;
import com.playmusical.playmusicalweb.dto.ReservationDTO;
import com.playmusical.playmusicalweb.entity.Musical;
import com.playmusical.playmusicalweb.entity.MusicalCalendar;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface MusicalService {

    List<MusicalDTO> musicalListAll();

    List<ReservationDTO> reservationDTOList_CLI(String userId);

    Map<String, Object> reservationSeatList_CLI(String userId, Long reservationNo);

    Map<String, Object> findByPerformanceNo(Long performance_no);


    default MusicalDTO musicalToDTO(Musical musicalEntity) {
        return MusicalDTO.builder()
            .musicalNo(musicalEntity.getMusicalNo())
            .musicalTitle(musicalEntity.getMusicalTitle())
            .startDate(musicalEntity.getStartDate())
            .endDate(musicalEntity.getEndDate())
            .location(musicalEntity.getLocation())
            .startTime(musicalEntity.getStartTime())
            .endTime(musicalEntity.getEndTime())
            .posterImg(musicalEntity.getPosterImg())
            .bannerImg(musicalEntity.getBannerImg()).build();

    }

    PageResultDTO<MusicalDTO, Musical> musicalTitleList(String title,
        PageRequestDTO pageRequestDTO);

    default MusicalDTO musicalTitleListToDTO(Musical musicalEntity) {

        return MusicalDTO.builder()
            .musicalNo(musicalEntity.getMusicalNo())
            .musicalTitle(musicalEntity.getMusicalTitle())
            .location(musicalEntity.getLocation())
            .posterImg(musicalEntity.getPosterImg())
            .startDate(musicalEntity.getStartDate())
            .endDate(musicalEntity.getEndDate()).build();
    }

    int musicalTitleTotalSize(String title);

    MusicalDTO musicalDetail(Long musical_no);

    Long performanceNo(String musicalNo, LocalDate performanceDate);

    default MusicalCalendarDTO musicalCalendarToDTO(MusicalCalendar musicalCalendarEntity) {
        return MusicalCalendarDTO.builder()
            .performanceNo(musicalCalendarEntity.getPerformanceNo())
            .performanceData(musicalCalendarEntity.getPerformanceDate())
            .musicalDTO(musicalToDTO(musicalCalendarEntity.getMusical()))
            .cancelDate(musicalCalendarEntity.getCancelDate()).build();
    }

    Long register(MusicalDTO musicalDTO);

    default MusicalDTO entitiesToDTO(Musical musical, List<MusicalCalendar> musicalCalendars) {
        MusicalDTO musicalDTO = MusicalDTO.builder()
            .musicalNo(musical.getMusicalNo())
            .musicalTitle(musical.getMusicalTitle())
            .startDate(musical.getStartDate())
            .endDate(musical.getEndDate())
            .startTime(musical.getStartTime())
            .endTime(musical.getEndTime())
            .posterImg(musical.getPosterImg())
            .bannerImg(musical.getBannerImg())
            .location(musical.getLocation())
            .build();

        List<MusicalCalendarDTO> musicalCalendarDTOList = musicalCalendars.stream()
            .map(musicalCalendar -> MusicalCalendarDTO.builder()
                .musicalDTO(musicalDTO)
                .cancelDate(musicalCalendar.getCancelDate())
                .performanceNo(musicalCalendar.getPerformanceNo())
                .performanceData(musicalCalendar.getPerformanceDate())
                .build()).collect(Collectors.toList());

        musicalDTO.setMusicalCalendarDTOList(musicalCalendarDTOList);

        return musicalDTO;
    }

    default Map<String, Object> dtoToEntity(MusicalDTO musicalDTO) {
        Map<String, Object> entityMap = new HashMap<>();

        LocalDate localDate = musicalDTO.getStartDate();
        LocalDate localDate2 = musicalDTO.getEndDate();
        LocalTime localDate3 = musicalDTO.getStartTime();
        LocalTime localDate4 = musicalDTO.getEndTime();

        Musical musical = Musical.builder()
            .musicalNo(musicalDTO.getMusicalNo())
            .musicalTitle(musicalDTO.getMusicalTitle())
            .startDate(localDate)
            .endDate(localDate2)
            .startTime(localDate3)
            .endTime(localDate4)
            .posterImg(musicalDTO.getPosterImg())
            .bannerImg(musicalDTO.getBannerImg())
            .location(musicalDTO.getLocation())
            .build();

        entityMap.put("musical", musical);

        List<MusicalCalendarDTO> musicalCalendarDTOList = musicalDTO.getMusicalCalendarDTOList();

        if (musicalCalendarDTOList != null && musicalCalendarDTOList.size() > 0) {

            List<MusicalCalendar> musicalCalendarList = musicalCalendarDTOList.stream()
                .map(musicalCalendarDTO -> MusicalCalendar.builder()
                    .musical(musical)
                    .cancelDate(musicalCalendarDTO.getCancelDate())
                    .performanceNo(musicalCalendarDTO.getPerformanceNo())
                    .performanceDate(musicalCalendarDTO.getPerformanceData())
                    .build()).collect(Collectors.toList());

            entityMap.put("musicalCalendarList", musicalCalendarList);

        }
        return entityMap;
    }

}
