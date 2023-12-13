package com.hoaxify.hoaxifybackend.file;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */
@Service
@EnableScheduling
public class FileCleanupService {

    private final FileAttachmentRepository fileAttachmentRepository;
    private final FileService fileService;

    public FileCleanupService(FileAttachmentRepository fileAttachmentRepository, FileService fileService) {
        this.fileAttachmentRepository = fileAttachmentRepository;
        this.fileService = fileService;
    }

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)//24 saate bir calisicak olan bir servis
    public void cleanUpStorage() {
        // son 24 sattaede upload edilimsi ama her hangi bir hoax ile ilisklendirrilmemis
        Date twentyFourHoursAgo = new Date(System.currentTimeMillis() - (24 * 60 * 60 * 1000));
        fileAttachmentRepository.findByDateBeforeAndHoaxIsNull(twentyFourHoursAgo)
                .stream().forEach(fileAttachment -> {
                    fileService.deleteFileAttachment(fileAttachment.getFileName());
                    fileAttachmentRepository.delete(fileAttachment);
                });
    }
}
