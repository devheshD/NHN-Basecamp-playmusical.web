package com.playmusical.playmusicalweb.controller;

import com.playmusical.playmusicalweb.dto.MusicalCalendarDTO;
import com.playmusical.playmusicalweb.dto.MusicalDTO;
import com.playmusical.playmusicalweb.service.MusicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Controller
@RequiredArgsConstructor
public class MypageController {

    private static final String SESSION_ID = "sessionId";
    final private MusicalService musicalService;

    @PostMapping("/user/mypage/detail")
    public String detail(Long performanceNo, Long reservationNo, Model model,
        HttpServletRequest request) {
        Map<String, Object> map = musicalService.findByPerformanceNo(performanceNo);
        MusicalDTO musicalDTO = (MusicalDTO) map.get("MusicalDto");
        MusicalCalendarDTO musicalCalendarDTO = (MusicalCalendarDTO) map.get("MusicalCalenderDto");

        model.addAttribute("MusicalDto", musicalDTO);
        model.addAttribute("MusicalCalenderDto", musicalCalendarDTO);
        model.addAttribute("reservationNo", reservationNo);
        model.addAttribute("performanceNo", performanceNo);
        model.addAttribute("sessionId", request.getAttribute(SESSION_ID));

        return "user/mypage_detail";
    }
}
