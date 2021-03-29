package com.playmusical.playmusicalweb.controller;

import com.playmusical.playmusicalweb.dto.MusicalCalendarDTO;
import com.playmusical.playmusicalweb.dto.MusicalDTO;
import com.playmusical.playmusicalweb.service.MusicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private static final String USER_ID = "userId";
    private static final String SESSION_ID = "sessionId";

    final private MusicalService musicalService;

    @GetMapping("/check/{performanceNo}")
    public String check(@PathVariable String performanceNo, Model model,
        HttpServletRequest request) {
        model.addAttribute(SESSION_ID, request.getAttribute(SESSION_ID));
        model.addAttribute(USER_ID, request.getAttribute(USER_ID));

        Map<String, Object> map = musicalService.findByPerformanceNo(Long.valueOf(performanceNo));
        MusicalDTO musicalDTO = (MusicalDTO) map.get("MusicalDto");
        MusicalCalendarDTO musicalCalendarDTO = (MusicalCalendarDTO) map.get("MusicalCalenderDto");
        model.addAttribute("MusicalDto", musicalDTO);
        model.addAttribute("performanceDate", musicalCalendarDTO.getPerformanceData());
        model.addAttribute("cancelDate", musicalCalendarDTO.getCancelDate());
        model.addAttribute("startTime", musicalDTO.getStartTime());
        model.addAttribute("endTime", musicalDTO.getEndTime());
        model.addAttribute("MusicalCalenderDto", musicalCalendarDTO);
        model.addAttribute("performanceNo", performanceNo);

        return "reservation/reservation2";

    }

    @GetMapping("/choice/{performanceNo}")
    public String choice(@PathVariable String performanceNo, Model model,
        HttpServletRequest request) {
        model.addAttribute(SESSION_ID, request.getAttribute(SESSION_ID));
        model.addAttribute(USER_ID, request.getAttribute(USER_ID));
        model.addAttribute("performanceNo", performanceNo);
        return "reservation/reservation";
    }

}
