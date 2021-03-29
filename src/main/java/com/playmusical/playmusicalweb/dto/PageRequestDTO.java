package com.playmusical.playmusicalweb.dto;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class PageRequestDTO {

    private int page;
    private int size;
    private String type;
    private String keyword;


    public PageRequestDTO() {
        this.page = 1;
        this.size = 5;
    }

    public Pageable getPageable(Sort sort) {

        return PageRequest.of(page - 1, size, sort);

    }

    public Pageable getPageable() {

        return PageRequest.of(page - 1, size);

    }
}