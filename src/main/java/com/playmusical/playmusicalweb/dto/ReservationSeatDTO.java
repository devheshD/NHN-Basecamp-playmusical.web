package com.playmusical.playmusicalweb.dto;


import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReservationSeatDTO {

    private Long seatNo;

    private int seatNumber;

    private int reservationNo;

    private int performanceNo;

    private SeatDTO seat;
}
