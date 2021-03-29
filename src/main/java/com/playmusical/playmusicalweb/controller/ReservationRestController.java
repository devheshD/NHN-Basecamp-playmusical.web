package com.playmusical.playmusicalweb.controller;


import com.playmusical.playmusicalweb.dto.OccupyDTO;
import com.playmusical.playmusicalweb.dto.SeatDTO;
import com.playmusical.playmusicalweb.service.RedisService;
import com.playmusical.playmusicalweb.service.SeatService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seat")
@RequiredArgsConstructor
public class ReservationRestController {

    private final SeatService seatService;
    private final RedisService redisService;

    @PostMapping("/{performanceNo}")
    public ResponseEntity<List<SeatDTO>> findAllReservedSeats(@PathVariable String performanceNo) {
        return new ResponseEntity<>(redisService.getAll(performanceNo), HttpStatus.OK);
    }

    @GetMapping("/{seatNo}")
    public ResponseEntity<SeatDTO> getSeat(@PathVariable String seatNo) {
        return new ResponseEntity<>(seatService.findBySeatNo(seatNo), HttpStatus.OK);
    }

    @GetMapping("/occupy/{id}/{performanceNo}/{seatNo}")
    public ResponseEntity<Void> getOccupy(@PathVariable String id,
        @PathVariable String performanceNo, @PathVariable String seatNo) {
        return redisService.insert(id, performanceNo, seatNo) ? new ResponseEntity<>(HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/occupy/{id}/{performanceNo}/{seatNo}")
    public ResponseEntity<Void> cancelOccupy(@PathVariable String id,
        @PathVariable String performanceNo, @PathVariable String seatNo) {
        return redisService.delete(id, performanceNo, seatNo) ? new ResponseEntity<>(HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //    @PostMapping("/reservation")
    //    public ResponseEntity<Void> reserve(@RequestBody List<OccupyDTO> reserveInfo) {
    //        return redisService.reserve(reserveInfo) ? new ResponseEntity<>(HttpStatus.OK)
    //            : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    //        return
    //    }

    @PostMapping("/reservation2/{performanceNo}")
    public ResponseEntity<List<SeatDTO>> reserveList(@PathVariable String performanceNo,
        HttpServletRequest request) {
        List<Integer> seatNoList = redisService
            .seatNoList(request.getAttribute("userId").toString(), performanceNo);
        return new ResponseEntity<>(seatService.findSeatList(seatNoList), HttpStatus.OK);
    }

    @DeleteMapping("/reservation2")
    public ResponseEntity<Void> cancelAllOccupy(@RequestBody List<OccupyDTO> reserveInfo) {
        redisService.deleteAll(reserveInfo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/finish")
    public ResponseEntity<Void> finishReservation(@RequestBody List<OccupyDTO> reserveInfo) {
        return redisService.finishReserve(reserveInfo) ? new ResponseEntity<>(HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
