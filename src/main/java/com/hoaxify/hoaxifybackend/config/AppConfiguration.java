package com.hoaxify.hoaxifybackend.config;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "hoaxify")
public class AppConfiguration {

    private String uploadPath;

    private String profileImage = "profile";

    private String attachmentFile = "attachments";

    public String getProfileImagePath() {
        return uploadPath + "/" + profileImage;
    }

    public String getAttachmentFilePath() {
        return uploadPath + "/" + attachmentFile;
    }

}
