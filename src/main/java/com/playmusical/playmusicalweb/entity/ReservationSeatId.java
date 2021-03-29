package com.playmusical.playmusicalweb.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReservationSeatId implements Serializable {

    private int seatNo;
    private Long reservationNo;
    private Long performanceNo;
}
