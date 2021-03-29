package com.playmusical.playmusicalweb.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SessionUserDTO {

    private String id;

    private String password;

    private String url;

    private String name;

    private Long userNo;
}
