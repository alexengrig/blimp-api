package blimp.api.service;

import blimp.api.configuration.LocalStorageConfig;
import blimp.api.exception.FileSystemException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class LocalFileSystemService implements FileSystemService {
    private Path temp;

    public LocalFileSystemService(LocalStorageConfig localStorageConfig) {
        this.temp = Paths.get(localStorageConfig.getPath());
    }

    @Override
    public Path get(URI uri) {
        return Paths.get(uri);
    }

    @Override
    public Path transfer(MultipartFile file) throws FileSystemException {
        try {
            String originalFilename = file.getOriginalFilename();
            Path target = Files.createTempFile(temp, originalFilename, "-blimp");
            file.transferTo(target);
            return target;
        } catch (IOException e) {
            throw new FileSystemException("Exception of transfer file", e);
        }
    }
}
