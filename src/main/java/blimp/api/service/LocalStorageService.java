package blimp.api.service;

import blimp.api.domain.File;
import blimp.api.exception.FileSystemException;
import blimp.api.exception.StorageException;
import blimp.api.repository.StorageRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class LocalStorageService implements StorageService {
    private final StorageRepository storageRepository;
    private final FileSystemService fileSystemService;

    @Override
    public File getFileById(Long fileId) {
        return storageRepository.findById(fileId)
                .orElseThrow(() -> new StorageException(String.format("File not found by id: %d", fileId)));
    }

    @Override
    public File store(MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String filename = FilenameUtils.removeExtension(originalFilename);
            String extension = FilenameUtils.getExtension(originalFilename);

            Path transferredFile = fileSystemService.transfer(file);
            File newFile = File.builder()
                    .name(filename)
                    .extension(extension)
                    .uri(transferredFile.toUri())
                    .creationDate(LocalDateTime.now())
                    .build();

            return storageRepository.save(newFile);
        } catch (FileSystemException e) {
            throw new StorageException("Exception of store file", e);
        }
    }

}
