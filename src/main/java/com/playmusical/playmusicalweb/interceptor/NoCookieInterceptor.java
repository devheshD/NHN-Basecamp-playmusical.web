package com.playmusical.playmusicalweb.interceptor;

import com.playmusical.playmusicalweb.service.SessionCookieService;
import com.playmusical.playmusicalweb.service.SessionDBService;
import java.io.IOException;
import java.time.LocalDateTime;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

public class NoCookieInterceptor implements HandlerInterceptor {

    private static final String USER_ID = "userId";
    private static final String SESSION_ID = "sessionId";

    @Autowired
    private SessionCookieService sessionCookieService;

    @Autowired
    private SessionDBService sessionDBService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj)
        throws IOException {
        Cookie[] cookies = request.getCookies();
        Cookie cookie = null;
        if (cookies == null) {
            return true;
        }
        for (Cookie c : cookies) {
            if (c.getName().equals(SESSION_ID)) {
                cookie = c;
                break;
            }
        }
        if (cookie == null) {
            return true;
        }
        String token = cookie.getValue();
        if (!sessionDBService.isExist(token)) {
            sessionDBService.delete(token);
            sessionCookieService.deleteCookie(response);
            response.sendRedirect("/user/login");
            return true;
        }
        String userId = sessionDBService.getUserId(token);
        sessionDBService.updateExpiredTime(token, LocalDateTime.now().plusMinutes(10));
        sessionCookieService.makeCookie(response, token, 600);
        request.setAttribute(USER_ID, userId);
        request.setAttribute(SESSION_ID, token);
        return true;
    }

}
