package com.playmusical.playmusicalweb.dto;

import lombok.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MusicalCalendarDTO {

    private Long performanceNo;

    private LocalDateTime performanceData;

    private MusicalDTO musicalDTO;

    private LocalDateTime cancelDate;

}
