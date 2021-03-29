package com.playmusical.playmusicalweb.controller;

import com.playmusical.playmusicalweb.dto.PageRequestDTO;
import com.playmusical.playmusicalweb.service.APIService;
import com.playmusical.playmusicalweb.service.ReservationService;
import com.playmusical.playmusicalweb.service.SessionCookieService;
import com.playmusical.playmusicalweb.service.SessionDBService;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private static final String USER_ID = "userId";
    private static final String SESSION_ID = "sessionId";
    private final SessionDBService sessionDBService;
    private final SessionCookieService sessionCookieService;
    private final APIService apiService;
    private final ReservationService reservationService;

    @GetMapping("/mypage")
    public String mypage(Model model, HttpServletRequest request, PageRequestDTO pageRequestDTO) {
        model.addAttribute(SESSION_ID, request.getAttribute(SESSION_ID));
        model.addAttribute(USER_ID, request.getAttribute(USER_ID));
        model.addAttribute("musicalTitleList", reservationService
            .getReservationInfoList(request.getAttribute(USER_ID).toString(), pageRequestDTO));
        model.addAttribute("pageRequestDTO", pageRequestDTO);
        return "user/mypage_list";

    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("referer", request.getHeader("Referer"));
        cookie.setHttpOnly(true);
        cookie.setMaxAge(600);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "user/login";
    }

    @GetMapping("/register")
    public String register() {
        return "user/register";
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerPost(@RequestBody String userData) {
        apiService.register(userData);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/userIdChk")
    public ResponseEntity<Void> userIdChkPost(String id) {
        return apiService.idCheck(id);
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        sessionDBService.delete((String) request.getAttribute(SESSION_ID));
        sessionCookieService.deleteCookie(response);
        return "redirect:/";
    }

    @GetMapping("/isLogin")
    public ResponseEntity<Void> checkSession(HttpServletRequest request) {
        if (sessionDBService.isExist((String) request.getAttribute(SESSION_ID))) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/dooray")
    public ResponseEntity<String> doorayURL(@RequestBody String id) {
        return apiService.dooray(id);
    }

    @GetMapping("/accesslogin")
    public String accessLoginCheck(String id, String token, HttpServletResponse response,
        HttpServletRequest request) {
        sessionDBService.delete(token);
        sessionCookieService.deleteCookie(response);
        if (apiService.login(id, token).getStatusCodeValue() == 200) {
            sessionDBService.insert(token, id);
            sessionCookieService.makeCookie(response, token, 600);
            String url = null;
            if (request.getCookies() != null) {
                for (Cookie c : request.getCookies()) {
                    if (c.getName().equals("referer")) {
                        url = c.getValue();
                        c.setMaxAge(0);
                        break;
                    }
                }
            }
            return url == null ? "redirect:/" : "redirect:" + url;
        } else {
            return "user/login";
        }
    }

    @PostMapping("/totalLogin/{urlName}")
    @ResponseBody
    public ResponseEntity<String> loginAuth(@RequestBody String userData,
        @PathVariable String urlName) {
        return apiService.loginAuth(userData, urlName);
    }


}
