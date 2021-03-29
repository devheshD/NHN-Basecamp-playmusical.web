package com.playmusical.playmusicalweb.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SessionUser {

    @Id
    private String token;

    private String userId;

    private LocalDateTime expiredTime;

    public SessionUser(String token, String userId) {
        this.token = token;
        this.userId = userId;
    }

    // insert 전에 작동
    @PrePersist
    protected void prePersist() {
        this.expiredTime = LocalDateTime.now().plusMinutes(10);
    }

}
