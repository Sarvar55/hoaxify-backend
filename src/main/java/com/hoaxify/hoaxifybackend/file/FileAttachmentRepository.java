package com.hoaxify.hoaxifybackend.file;

import com.hoaxify.hoaxifybackend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */
public interface FileAttachmentRepository extends JpaRepository<FileAttachment, Long> {

    List<FileAttachment> findByDateBeforeAndHoaxIsNull(Date date);

    List<FileAttachment> findByHoaxUser(User user);

}
