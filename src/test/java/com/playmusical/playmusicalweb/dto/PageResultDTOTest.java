package com.playmusical.playmusicalweb.dto;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PageResultDTOTest<DTO, EN> {
    @Test
    void setPageResultDTO() {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        Page<String> page = new PageImpl<>(list, PageRequest.of(1,2),1);
        Function<String, String> fn = (s -> s + "1");
        PageResultDTO<String, String> pageResultDTO = new PageResultDTO<>(page, fn);
        assertEquals(2, pageResultDTO.getSize());
        assertEquals(2,pageResultDTO.getDtoList().size());
        assertEquals(2,pageResultDTO.getDtoList().size());
        assertEquals(2,pageResultDTO.getTotalPage());
        assertEquals(2,pageResultDTO.getPage());
        assertEquals(1,pageResultDTO.getStart());
        assertEquals(2,pageResultDTO.getEnd());
        assertFalse(pageResultDTO.isPrev());
        assertFalse(pageResultDTO.isNext());
        assertEquals(2,pageResultDTO.getPageList().size());
    }
}