package com.playmusical.playmusicalweb.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SessionUserDTOTest {
    private SessionUserDTO sessionUserDto;

    @BeforeEach
    void setUp() {
        sessionUserDto = SessionUserDTO.builder().id("qwer1234")
                .password("12345")
                .url("doorayUrl")
                .name("홍길동")
                .userNo(1L).build();
    }

    @Test
    void getUserTest() {
        assertEquals(1L, sessionUserDto.getUserNo());
        assertEquals("qwer1234", sessionUserDto.getId());
        assertEquals("12345", sessionUserDto.getPassword());
        assertEquals("doorayUrl", sessionUserDto.getUrl());
        assertEquals("홍길동", sessionUserDto.getName());
    }

    @Test
    void setUserTest() {
        SessionUserDTO setSessionUserDto = new SessionUserDTO();
        setSessionUserDto.setUserNo(2L);
        setSessionUserDto.setId("qwer");
        setSessionUserDto.setName("홍길동");
        setSessionUserDto.setPassword("12345");
        setSessionUserDto.setUrl("doorayUrl");


        assertEquals(2L, setSessionUserDto.getUserNo());
        assertEquals("qwer", setSessionUserDto.getId());
        assertEquals("홍길동", setSessionUserDto.getName());
        assertEquals("12345", setSessionUserDto.getPassword());
        assertEquals("doorayUrl", setSessionUserDto.getUrl());

    }
}
