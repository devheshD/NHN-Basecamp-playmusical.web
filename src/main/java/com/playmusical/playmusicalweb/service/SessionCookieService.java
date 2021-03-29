package com.playmusical.playmusicalweb.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class SessionCookieService {

    private static final String SESSION_ID = "sessionId";

    public void makeCookie(HttpServletResponse response, String token, int maxAge) {
        Cookie cookie = new Cookie(SESSION_ID, token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public void deleteCookie(HttpServletResponse response) {
        Cookie token = new Cookie(SESSION_ID, null);
        token.setHttpOnly(true);
        token.setMaxAge(0);
        token.setPath("/");
        response.addCookie(token);
    }

}
