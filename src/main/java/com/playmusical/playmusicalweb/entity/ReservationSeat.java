package com.playmusical.playmusicalweb.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class ReservationSeat {

    @Id
    @EmbeddedId
    private ReservationSeatId reservationSeatId;

    @MapsId("reservationNo")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reservation_no")
    private Reservation reservation;


    @MapsId("seatNo")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seat_no")
    private Seat seatNo;

}
