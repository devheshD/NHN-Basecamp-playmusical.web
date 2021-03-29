package com.playmusical.playmusicalweb.service;

import com.playmusical.playmusicalweb.dto.UserDTO;
import com.playmusical.playmusicalweb.enums.TFIpList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class APIService {

    private final RestTemplate restTemplate;

    @Value("${api.server.host}")
    private String URL;

    public ResponseEntity<String> register(String userData) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(userData, headers);
        boolean result = restTemplate.postForEntity(URL + "api/register", entity, Boolean.class)
            .getBody();
        return result ? new ResponseEntity<>(HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Void> idCheck(String id) {
        boolean result = restTemplate.postForEntity(URL + "api/userIdChk", id, Boolean.class)
            .getBody();
        return result ? new ResponseEntity<>(HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> dooray(String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(id, headers);
        String result = restTemplate.postForEntity(URL + "api/dooray", entity, String.class)
            .getBody();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<String> login(String id, String token) {
        UserDTO user = UserDTO.builder().id(id).token(token).build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserDTO> entity = new HttpEntity<>(user, headers);
        return restTemplate.exchange(URL + "api/login", HttpMethod.POST, entity, String.class);
    }

    public ResponseEntity<String> loginAuth(String userData, String urlName) {
        String url;
        if (urlName == null || urlName.equals("saecom")) {
            url = URL + TFIpList.saecom.getUrl();
        } else {
            url = TFIpList.valueOf(urlName).getUrl();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(userData, headers);
        ResponseEntity<String> responseEntity = restTemplate
            .postForEntity(url, entity, String.class);
        return responseEntity;
    }
}
