package com.playmusical.playmusicalweb.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PageRequestDTOTest {

    @Test
    void setPageRequestDTOTest() {
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        pageRequestDTO.setPage(1);
        pageRequestDTO.setKeyword("test");
        pageRequestDTO.setSize(1);
        pageRequestDTO.setType("test");
        assertEquals(1, pageRequestDTO.getPage());
        assertEquals(1, pageRequestDTO.getSize());
        assertEquals("test", pageRequestDTO.getKeyword());
        assertEquals("test", pageRequestDTO.getType());
    }
}
