package com.tksystem.ecommerceplatform.domain.service.common;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    public Long saveFile(MultipartFile file, String subDir);

}
