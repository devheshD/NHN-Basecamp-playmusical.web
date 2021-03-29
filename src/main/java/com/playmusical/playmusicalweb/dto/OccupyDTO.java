package com.playmusical.playmusicalweb.dto;

import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OccupyDTO implements Serializable {

    private String userId;

    private Long performanceNo;

    private int seatNo;
}
