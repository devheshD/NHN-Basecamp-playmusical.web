package com.playmusical.playmusicalweb.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    private String id;

    private String token;

}
