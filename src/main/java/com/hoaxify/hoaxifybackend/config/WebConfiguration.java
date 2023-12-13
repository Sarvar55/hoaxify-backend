package com.hoaxify.hoaxifybackend.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */
@Configuration
@Slf4j
public class WebConfiguration implements WebMvcConfigurer {
    //    @Value("${upload-path}")
//    private String uploadPath;
    private final AppConfiguration appConfiguration;

    public WebConfiguration(AppConfiguration appConfiguration) {
        this.appConfiguration = appConfiguration;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:./" + appConfiguration.getUploadPath().concat(File.separator))
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));

    }


    @Bean
    public CommandLineRunner createStorages() {
        return (args) -> {
            createStorageDirectory(appConfiguration.getUploadPath());
            createStorageDirectory(appConfiguration.getAttachmentFilePath());
            createStorageDirectory(appConfiguration.getProfileImagePath());
        };
    }

    private void createStorageDirectory(String path) {
        File folder = new File(path);
        boolean folderExists = folder.exists() && folder.isDirectory();
        if (!folderExists) {
            folder.mkdir();
        }
    }
}
