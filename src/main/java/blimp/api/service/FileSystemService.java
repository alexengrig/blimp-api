package blimp.api.service;


import blimp.api.exception.FileSystemException;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.nio.file.Path;

public interface FileSystemService {
    Path get(URI uri);

    Path transfer(MultipartFile file) throws FileSystemException;
}
