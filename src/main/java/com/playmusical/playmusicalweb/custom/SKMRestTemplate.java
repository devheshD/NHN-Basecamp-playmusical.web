package com.playmusical.playmusicalweb.custom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Log4j2
public class SKMRestTemplate<T> extends RestTemplate {

    @Value("${SKM.appkey}")
    private String APPKEY;

    @Value("${SKM.URL}")
    private String SKMURL;

    final private ObjectMapper objectMapper = new ObjectMapper();

    public Map<String, String> URLInfo(String APPKEY, String KEYID) {
        Map<String, String> map = new HashMap<>();
        map.put("APPKEY", APPKEY);
        map.put("KEYID", KEYID);
        return map;
    }

    public T getInfo(String KEYID, final Class<T> type) {
        String URL = new StringSubstitutor(URLInfo(APPKEY, KEYID)).replace(SKMURL);
        ResponseEntity<String> responseEntity = getForEntity(URL, String.class);
        String secret;
        T result = null;
        try {
            secret = objectMapper.readValue(responseEntity.getBody(), SKMResponse.class).getBody()
                .getSecret();
            result = objectMapper.readValue(secret, type);
        } catch (JsonProcessingException e) {
            log.error("Failed to parse SKM");
            e.printStackTrace();
        }
        return result;
    }

    @Getter
    @Setter
    public static class SKMResponse {

        private Header header;
        private Body body;
    }

    @Getter
    @Setter
    public static class Header {

        private String resultCode;
        private String resultMessage;
        private String isSuccessful;
    }

    @Getter
    @Setter
    public static class Body {

        private String secret;
    }
}
