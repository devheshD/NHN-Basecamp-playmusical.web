package com.playmusical.playmusicalweb.controller;

import com.playmusical.playmusicalweb.dto.PageRequestDTO;
import com.playmusical.playmusicalweb.dto.PageResultDTO;
import com.playmusical.playmusicalweb.entity.Reservation;
import com.playmusical.playmusicalweb.entity.ReservationSeat;
import com.playmusical.playmusicalweb.service.MusicalService;
import com.playmusical.playmusicalweb.service.ReservationSeatService;
import com.playmusical.playmusicalweb.service.ReservationService;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/mypage")
@RestController
@RequiredArgsConstructor
public class MypageRestController {

    private static final String USER_ID = "userId";
    private final MusicalService musicalService;
    private final ReservationService reservationService;
    private final ReservationSeatService reservationSeatService;

    // 예약 목록 리스트
    @PostMapping("/")
    public ResponseEntity<PageResultDTO<Reservation, Reservation>> myPageList(
        HttpServletRequest request, PageRequestDTO pageRequestDTO) {
        String userId = request.getAttribute(USER_ID).toString();
        return new ResponseEntity<>(
            reservationService.getReservationInfoList(userId, pageRequestDTO), HttpStatus.OK);
    }

    @PostMapping("/list")
    public Map<String, Object> reservationList(Long performanceNo) {
        return musicalService.findByPerformanceNo(performanceNo);
    }

    @PostMapping("/reservation")
    public List<ReservationSeat> reservationSeatList(Long reservationNo) {
        return reservationSeatService.findByReservationSeatId_ReservationNo(reservationNo);
    }

    @PostMapping("/cancel")
    public ResponseEntity<Void> reservationCancel(Long reservationNo, Long performanceNo) {
        reservationSeatService.deleteReservation(reservationNo, performanceNo);
        reservationService.cancelReservation(reservationNo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //        @GetMapping("/mypage2/{userNo}")
    //        public List<Reservation> mypageList_CLI(String userId) {
    //            return new ArrayList<>(reservationService.getReservationInfoList(userId));
    //        }

    @GetMapping("/reservation2/{reservationNo}")
    public List<ReservationSeat> reservationSeatList_CLI(@PathVariable Long reservationNo) {
        return reservationSeatService.findByReservationSeatId_ReservationNo(reservationNo);
    }


}
