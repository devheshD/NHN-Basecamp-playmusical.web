package com.playmusical.playmusicalweb.command;


import com.playmusical.playmusicalweb.dto.MusicalCalendarDTO;
import com.playmusical.playmusicalweb.dto.MusicalDTO;
import com.playmusical.playmusicalweb.dto.ReservationDTO;
import com.playmusical.playmusicalweb.service.MusicalService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class UserCommand {

    private final MusicalService musicalService;

    @ShellMethod(value = "{id} 예매 내역 조회", key = "rsvs")
    public String reservationList(String userId) {
        StringBuilder reservationList = new StringBuilder();
        reservationList.append("------예매 내역 조회-------");

        List<ReservationDTO> reservationDTOList = musicalService.reservationDTOList_CLI(userId);

        for (ReservationDTO reservationDTO : reservationDTOList) {
            Long performanceNo = reservationDTO.getPerformanceNo();

            Map<String, Object> map = musicalService.findByPerformanceNo(performanceNo);
            MusicalDTO musicalDTO = (MusicalDTO) map.get("MusicalDto");
            MusicalCalendarDTO musicalCalendarDTO = (MusicalCalendarDTO) map
                .get("MusicalCalenderDto");

            reservationList.append("\n뮤지컬 제목: ").append(musicalDTO.getMusicalTitle())
                .append("\n공연 날짜: ").append(musicalCalendarDTO.getPerformanceData())
                .append("\n위치: ").append(musicalDTO.getLocation()).append("\n시작 시간: ")
                .append(musicalDTO.getStartTime()).append("\n종료 시간: ")
                .append(musicalDTO.getEndTime()).append("\n취소가능일: ")
                .append(musicalCalendarDTO.getCancelDate()).append("\n--------------------------");
        }

        return reservationList.toString();
    }

    @ShellMethod(value = "{id} {예매번호} 예매 내역 상세 조회", key = "dtl")
    public String reservationDetailList(String userId, Long reservationNo) {

        StringBuilder reservationDetailList = new StringBuilder();
        reservationDetailList.append("------예매 내역 상세 조회-------");

        Map<String, Object> reservationInfo = musicalService
            .reservationSeatList_CLI(userId, reservationNo);

        String seatList = reservationInfo.get("SeatList").toString();
        Map<String, Object> map = (Map<String, Object>) reservationInfo.get("MusicalInfo");
        if (map == null) {
            return "예매 내역이 없습니다.";
        }

        MusicalDTO musicalDTO = (MusicalDTO) map.get("MusicalDto");
        MusicalCalendarDTO musicalCalendarDTO = (MusicalCalendarDTO) map.get("MusicalCalenderDto");

        reservationDetailList.append("\n뮤지컬 제목: ").append(musicalDTO.getMusicalTitle())
            .append("\n공연 날짜: ").append(musicalCalendarDTO.getPerformanceData()).append("\n위치: ")
            .append(musicalDTO.getLocation()).append("\n시작 시간: ").append(musicalDTO.getStartTime())
            .append("\n종료 시간: ").append(musicalDTO.getEndTime()).append("\n취소가능일: ")
            .append(musicalCalendarDTO.getCancelDate()).append("\n--------------------------");
        //        for (int i = 0; i < jsonArray.length(); i++) {
        //            JSONObject reservationList = (JSONObject) jsonArray.get(i);
        //            JSONObject seatNo = (JSONObject) reservationList.get("seatNo");
        //            reservationDetailList.append("\n" + seatNo.get("seatRank") + " " + seatNo.get("seatNo"));
        //        }

        reservationDetailList.append("\n--------------------------");
        return reservationDetailList.toString();

    }
}
