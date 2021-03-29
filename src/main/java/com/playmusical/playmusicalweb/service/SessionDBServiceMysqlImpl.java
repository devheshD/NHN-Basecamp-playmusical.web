package com.playmusical.playmusicalweb.service;


import com.playmusical.playmusicalweb.entity.SessionUser;
import com.playmusical.playmusicalweb.repository.SessionUserRepo;
import java.time.LocalDateTime;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;


public class SessionDBServiceMysqlImpl implements SessionDBService {

    @Autowired
    private SessionUserRepo sessionUserRepo;

    @Override
    public boolean isExist(String token) {
        return sessionUserRepo.existsByTokenAndExpiredTimeAfter(token, LocalDateTime.now());
    }


    @Transactional
    @Override
    public void insert(String token, String userId) {
        sessionUserRepo.save(new SessionUser(token, userId));
    }

    @Transactional
    @Override
    public void delete(String token) {
        sessionUserRepo.deleteByToken(token);
    }

    @Override
    public String getUserId(String token) {
        return sessionUserRepo.findByTokenAndExpiredTimeAfter(token, LocalDateTime.now()).get()
            .getUserId();
    }

    @Override
    public void updateExpiredTime(String token, LocalDateTime updatedTime) {
        sessionUserRepo.updateExpiredTime(token, updatedTime);
    }


}
