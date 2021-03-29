package com.playmusical.playmusicalweb.config;

import com.playmusical.playmusicalweb.interceptor.NoCookieInterceptor;
import com.playmusical.playmusicalweb.interceptor.SessionCheckInterceptor;
import com.toast.java.logncrash.logback.LogbackShutdownListener;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Bean
    public SessionCheckInterceptor sessionCheckInterceptor() {
        return new SessionCheckInterceptor();
    }

    @Bean
    public NoCookieInterceptor noCookieInterceptor() {
        return new NoCookieInterceptor();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").maxAge(1800);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(noCookieInterceptor()).addPathPatterns("/**")
            .excludePathPatterns("/user/mypage", "/reservation/**", "/api/mypage/**", "/logout");

        registry.addInterceptor(sessionCheckInterceptor())
            .addPathPatterns("/user/mypage", "/reservation/**", "/api/mypage/**", "/logout");


    }

    @Bean
    public LogbackShutdownListener logbackShutdownListener() {
        return new LogbackShutdownListener();
    }

}
