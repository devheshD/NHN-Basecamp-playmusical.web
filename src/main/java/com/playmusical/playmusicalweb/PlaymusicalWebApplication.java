package com.playmusical.playmusicalweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableAspectJAutoProxy
public class PlaymusicalWebApplication {

    public static void main(String[] args) {
        String profile = System.getProperty("spring.profiles.active");
        if (profile == null) {
            System.setProperty("spring.profiles.active", "default");
        }
        SpringApplication.run(PlaymusicalWebApplication.class, args);
    }

}
