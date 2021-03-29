package com.playmusical.playmusicalweb.config;


import com.playmusical.playmusicalweb.service.SessionDBService;
import com.playmusical.playmusicalweb.service.SessionDBServiceMysqlImpl;
import com.playmusical.playmusicalweb.service.SessionDBServiceRedisImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionDatabaseSourceConfig {

    @Value("${session.type}")
    String sessionType;

    @Bean
    public SessionDBService sessionService() {
        if (sessionType.equals("MYSQL")) {
            return new SessionDBServiceMysqlImpl();
        } else {
            return new SessionDBServiceRedisImpl();
        }
    }

}
