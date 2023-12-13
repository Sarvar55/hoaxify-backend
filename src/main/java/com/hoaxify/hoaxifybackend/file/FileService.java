package com.hoaxify.hoaxifybackend.file;

import com.hoaxify.hoaxifybackend.config.AppConfiguration;
import com.hoaxify.hoaxifybackend.user.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */
@Service
@Slf4j
public class FileService {

    //        @Value("${upload-path}")
//        private String uploadPath;
    private final FileAttachmentRepository fileAttachmentRepository;
    private final AppConfiguration appConfiguration;
    private Tika tika;

    public FileService(FileAttachmentRepository fileAttachmentRepository, AppConfiguration appConfiguration) {
        this.fileAttachmentRepository = fileAttachmentRepository;
        this.tika = new Tika();
        this.appConfiguration = appConfiguration;
    }


    public String writeBase64EncodedStringToFile(String image) throws IOException {
        String fileName = generateRandomName();
        File target = new File(appConfiguration.getProfileImagePath(), "/" + fileName);
        OutputStream outputStream = new FileOutputStream(target);

        byte[] base64encoded = Base64.getDecoder().decode(image);

        outputStream.write(base64encoded);
        outputStream.close();
        return fileName;
    }
    //
    public String generateRandomName() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public void deleteProfileImage(String oldImageName) {
        if (oldImageName == null)
            return;
        String filePath = appConfiguration.getProfileImagePath() + "/" + oldImageName;
        deleteFile(Paths.get(filePath));
    }

    public void deleteFileAttachment(String fileAttachment) {
        if (fileAttachment == null)
            return;
        String filePath = appConfiguration.getAttachmentFilePath() + "/" + fileAttachment;
        deleteFile(Paths.get(filePath));
    }

    private void deleteFile(Path path) {
        if (path == null)
            return;
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String detectType(byte[] arr) {
        return tika.detect(arr);
    }

    public String detectType(String image) {
        byte[] base64encoded = Base64.getDecoder().decode(image);
        return detectType(base64encoded);
    }

    public FileAttachment saveHoaxAttacment(MultipartFile multipartFile) {
        String fileName = generateRandomName();
        File target = new File(appConfiguration.getAttachmentFilePath(), "/" + fileName);
        OutputStream outputStream = null;
        String fileType = null;
        try {
            byte[] arr = multipartFile.getBytes();
            outputStream = new FileOutputStream(target);
            outputStream.write(arr);
            fileType = detectType(arr);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileAttachment attachment = new FileAttachment();
        attachment.setFileName(fileName);
        attachment.setDate(new Date());
        attachment.setFileType(fileType);
        return fileAttachmentRepository.save(attachment);
    }

    public void removeAllStorageFilesForUser(User inDB) {
        if (inDB.getImage() != null) {
            deleteProfileImage(inDB.getImage());
        }
        log.info("RemoveAllStroageFİles");

        List<FileAttachment> filesToBeRemoved = fileAttachmentRepository.findByHoaxUser(inDB);
        for (FileAttachment fileAttachment : filesToBeRemoved) {
            System.out.println(fileAttachment.getFileName());
            deleteFileAttachment(fileAttachment.getFileName());
        }
    }
}
