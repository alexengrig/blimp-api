package blimp.api.controller;

import blimp.api.domain.File;
import blimp.api.service.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/storage")
@AllArgsConstructor
public class StorageController {
    private final StorageService storageService;

    @GetMapping
    public ResponseEntity<?> getInfo() {
        return ResponseEntity.ok("This is blimp storage API.");
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<File> getFile(@PathVariable Long fileId) {
        return ResponseEntity.ok(storageService.getFileById(fileId));
    }

    @PostMapping
    public ResponseEntity<?> saveFile(@RequestBody MultipartFile file) {
        return ResponseEntity.ok(storageService.store(file));
    }
}
