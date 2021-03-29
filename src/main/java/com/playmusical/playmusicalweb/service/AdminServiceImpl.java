package com.playmusical.playmusicalweb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.playmusical.playmusicalweb.custom.SKMRestTemplate;
import com.playmusical.playmusicalweb.dto.MusicalDTO;
import com.playmusical.playmusicalweb.util.ObjectAuthTokenUtil;
import com.playmusical.playmusicalweb.util.ObjectStorageUtil;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    final private String containerName = "Upload-Image/";
    final private ObjectMapper objectMapper;
    final private SKMRestTemplate<Secret> skmRestTemplate;
    @Value("${ObjectStorage.keyid}")
    private String keyId;
    @Value("${AUTHTOKEN.URL}")
    private String authTokenUrl;
    private String storageUrl = "https://api-storage.cloud.toast.com/v1/AUTH_%s";
    private ObjectStorageUtil objectStorageUtil;

    @Override
    public void AuthToken() {
        Secret secret = skmRestTemplate.getInfo(keyId, Secret.class);
        ObjectAuthTokenUtil authService = new ObjectAuthTokenUtil(authTokenUrl,
            secret.getTenantId(), secret.getUsername(), secret.getPassword());
        String result = authService.requestToken();
        JsonNode authResult = null;
        try {
            authResult = objectMapper.readTree(result);
        } catch (JsonProcessingException e) {
            log.error("Object Storage Access Error");
            e.printStackTrace();
        }
        JsonNode tokenObject = null;
        if (authResult != null) {
            tokenObject = authResult.get("access").get("token");
        }
        this.storageUrl = String.format(storageUrl, secret.getTenantId());
        objectStorageUtil = new ObjectStorageUtil(storageUrl, tokenObject.get("id").textValue());
    }

    @Override
    public void uploadFile(String objectName, MultipartFile multipartFile) {
        if (objectStorageUtil == null) {
            AuthToken();
        }
        try {
            InputStream inputStream = multipartFile.getInputStream();
            objectStorageUtil.uploadObject(containerName, objectName, inputStream);
        } catch (Exception e) {
            log.error("업로드 실패");
        }

    }

    @Override
    public String makeImageName(String originalFileName, String fileType) {
        String imageName = StringUtils.cleanPath(Objects.requireNonNull(originalFileName));
        String uuid = UUID.randomUUID().toString();
        return imageName + fileType + uuid;
    }

    @Override
    public void makeImageAndUploadFile(MusicalDTO musicalDTO, MultipartFile multipartFile,
        String fileType) {
        String imageName = StringUtils
            .cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        String resultImageName = makeImageName(imageName, fileType);

        if (fileType.equals("_banner_")) {
            musicalDTO.setBannerImg(resultImageName);
        } else if (fileType.equals("_poster_")) {
            musicalDTO.setPosterImg(resultImageName);
        }

        uploadFile(resultImageName, multipartFile);
    }

    @Override
    public ResponseEntity<byte[]> getFileResult(String objectName) {
        if (objectStorageUtil == null) {
            AuthToken();
        }
        try {
            InputStream inputStream = objectStorageUtil.downloadObject(containerName, objectName);
            byte[] image = IOUtils.toByteArray(inputStream);

            return new ResponseEntity<>(image, HttpStatus.OK);

        } catch (Exception e) {
            log.error("File download failed");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Getter
    @Setter
    public static class Secret {

        private String tenantId;
        private String username;
        private String password;
    }


}
