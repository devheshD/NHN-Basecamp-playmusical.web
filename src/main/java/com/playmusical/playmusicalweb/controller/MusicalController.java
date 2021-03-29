package com.playmusical.playmusicalweb.controller;

import com.playmusical.playmusicalweb.dto.MusicalDTO;
import com.playmusical.playmusicalweb.dto.PageRequestDTO;
import com.playmusical.playmusicalweb.service.APIService;
import com.playmusical.playmusicalweb.service.AdminService;
import com.playmusical.playmusicalweb.service.MusicalService;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MusicalController {

    private static final String SESSION_ID = "sessionId";
    private final MusicalService musicalService;
    private final AdminService adminService;
    private final APIService apiService;

    @GetMapping("/")
    public String main(Model model, HttpServletRequest request) {
        List<MusicalDTO> musicalListAll = musicalService.musicalListAll();
        model.addAttribute("musicalListAll", musicalListAll);
        model.addAttribute(SESSION_ID, request.getAttribute(SESSION_ID));
        return "index";
    }

    @GetMapping("/search")
    public String search(String title, Model model, PageRequestDTO pageRequestDTO,
        HttpServletRequest request) {
        model.addAttribute("musicalTitleList",
            musicalService.musicalTitleList(title, pageRequestDTO));
        model.addAttribute("pageRequestDTO", pageRequestDTO);
        model.addAttribute("musicalSearchTitle", title);
        model.addAttribute("searchCount", musicalService.musicalTitleTotalSize(title));
        model.addAttribute(SESSION_ID, request.getAttribute(SESSION_ID));
        return "main/search";
    }

    @GetMapping("/detail")
    public String detail(Long musicalNo, Model model, HttpServletRequest request) {
        MusicalDTO musicalDTO = musicalService.musicalDetail(musicalNo);
        LocalDate startDate = musicalDTO.getStartDate();
        LocalDate endDate = musicalDTO.getEndDate();
        LocalDate curDate = LocalDate.now();
        model.addAttribute("transDate", startDate);
        model.addAttribute("transEndDate", endDate);
        model.addAttribute("curDate", curDate);
        model.addAttribute("curYear", curDate.getYear());
        model.addAttribute("curMonth", curDate.getMonthValue());
        model.addAttribute("curDay", curDate.getDayOfMonth());
        model.addAttribute("musicalDTO", musicalDTO);
        model.addAttribute("startYear", startDate.getYear());
        model.addAttribute("startMonth", startDate.getMonthValue());
        model.addAttribute("startDay", startDate.getDayOfMonth());
        model.addAttribute("endYear", endDate.getYear());
        model.addAttribute("endMonth", endDate.getMonthValue());
        model.addAttribute("endDay", endDate.getDayOfMonth());
        model.addAttribute("getStartDate", musicalDTO.getStartDate());
        model.addAttribute(SESSION_ID, request.getAttribute(SESSION_ID));

        return "main/detail";
    }

    @GetMapping("/musicalNo")
    @ResponseBody
    public String detailNo(String musicalNo, String year, String month, String day) {
        LocalDate performanceDate = LocalDate
            .of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        return musicalService.performanceNo(musicalNo, performanceDate).toString();
    }


    @GetMapping("/display")
    public @ResponseBody
    ResponseEntity<byte[]> getFile(String fileName) {
        return adminService.getFileResult(fileName);
    }

    @PostMapping("/auth")
    @ResponseBody
    public ResponseEntity<String> loginAuth(@RequestBody String userData) {
        return apiService.loginAuth(userData, null);
    }
}
