package blimp.api.service;

import blimp.api.domain.File;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    File getFileById(Long fileId);

    File store(MultipartFile file);
}
