package com.hoaxify.hoaxifybackend.file;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */
@RestController
@RequestMapping("/api/1.0")
public class FileController {

    /**
     *
     */
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/hoax-attacments")
    public ResponseEntity<FileAttachment> saveHoaxAttacment(MultipartFile file) {
        return ResponseEntity.ok(fileService.saveHoaxAttacment(file));
    }
}
