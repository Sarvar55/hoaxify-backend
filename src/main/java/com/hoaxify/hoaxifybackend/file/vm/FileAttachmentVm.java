package com.hoaxify.hoaxifybackend.file.vm;

import com.hoaxify.hoaxifybackend.file.FileAttachment;
import lombok.Data;

/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */

@Data
public class FileAttachmentVm {
    private String name;
    private String fileType;

    public FileAttachmentVm(FileAttachment fileAttachment) {
        this.name = fileAttachment.getFileName();
        this.fileType = fileAttachment.getFileType();
    }
}
