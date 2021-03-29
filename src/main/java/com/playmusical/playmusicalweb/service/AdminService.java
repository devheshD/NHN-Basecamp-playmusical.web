package com.playmusical.playmusicalweb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.playmusical.playmusicalweb.dto.MusicalDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface AdminService {

    void AuthToken() throws JsonProcessingException;

    void uploadFile(String objectName, MultipartFile bannerFile);

    String makeImageName(String originalFileName, String fileType);

    ResponseEntity<byte[]> getFileResult(String fileName);

    void makeImageAndUploadFile(MusicalDTO musicalDTO, MultipartFile multipartFile,
        String fileType);

}
