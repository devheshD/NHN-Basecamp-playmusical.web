package com.playmusical.playmusicalweb.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReservationSeatDTOTest {
    private ReservationSeatDTO reservationSeatDTO;

    @BeforeEach
    void setUp() {
        reservationSeatDTO = ReservationSeatDTO.builder()
                .seatNo(1L)
                .seatNumber(1)
                .reservationNo(10)
                .performanceNo(10)
                .seat(new SeatDTO()).build();
    }

    @Test
    void getReservationSeatDtoTest() {
        assertEquals(1L, reservationSeatDTO.getSeatNo());
        assertEquals(1, reservationSeatDTO.getSeatNumber());
        assertEquals(10, reservationSeatDTO.getReservationNo());
        assertEquals(10, reservationSeatDTO.getPerformanceNo());

    }

    @Test
    void setReservationSeatDtoTest() {
        ReservationSeatDTO setReservateionSeatDto = new ReservationSeatDTO();
        setReservateionSeatDto.setSeatNo(2L);
        setReservateionSeatDto.setSeatNumber(2);
        setReservateionSeatDto.setReservationNo(10);
        setReservateionSeatDto.setPerformanceNo(10);

        assertEquals(2L, setReservateionSeatDto.getSeatNo());
        assertEquals(2, setReservateionSeatDto.getSeatNumber());
        assertEquals(10, setReservateionSeatDto.getReservationNo());
        assertEquals(10, setReservateionSeatDto.getPerformanceNo());

    }
}
