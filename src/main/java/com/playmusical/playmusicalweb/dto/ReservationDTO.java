package com.playmusical.playmusicalweb.dto;


import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReservationDTO {

    private Long reservationNo;

    private LocalDateTime reservationDate;

    private String userId;

    private Long performanceNo;

    private int reservationState;

}
