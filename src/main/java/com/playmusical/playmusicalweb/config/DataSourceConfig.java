package com.playmusical.playmusicalweb.config;

import com.playmusical.playmusicalweb.custom.SKMRestTemplate;
import javax.sql.DataSource;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {

    final private SKMRestTemplate<Secret> skmRestTemplate;
    @Value("${TicketDB.keyid}")
    private String KEYID;

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        Secret secret = skmRestTemplate.getInfo(KEYID, Secret.class);
        return dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver").url(secret.getUrl())
            .username(secret.getUsername()).password(secret.getPassword()).build();
    }

    @Getter
    @Setter
    public static class Secret {

        private String url;
        private String username;
        private String password;
    }
}
