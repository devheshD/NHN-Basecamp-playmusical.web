package com.playmusical.playmusicalweb.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SeatDTO {

    private int seatNo;

    private int seatPrice;

    private String seatRank;
}
