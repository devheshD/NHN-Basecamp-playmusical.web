package com.playmusical.playmusicalweb.service;


import java.time.LocalDateTime;

public interface SessionDBService {

    // 토큰 만기 체크
    boolean isExist(String token);

    // 로그인
    void insert(String token, String userId);

    // 로그아웃
    void delete(String token);

    // 유저ID 가져오기
    String getUserId(String token);

    // expired time 갱신
    void updateExpiredTime(String token, LocalDateTime updateTime);
}
