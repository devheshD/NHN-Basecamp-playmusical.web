package com.playmusical.playmusicalweb.repository;

import com.playmusical.playmusicalweb.entity.SessionUser;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SessionUserRepo extends JpaRepository<SessionUser, Long> {

    boolean existsByTokenAndExpiredTimeAfter(String token, LocalDateTime localDateTime);

    Optional<SessionUser> findByTokenAndExpiredTimeAfter(String token, LocalDateTime localDateTime);

    void deleteByToken(String token);

    @Modifying
    @Transactional
    @Query("update SessionUser s set s.expiredTime= :localDateTime where s.token = :token")
    void updateExpiredTime(String token, LocalDateTime localDateTime);
}
