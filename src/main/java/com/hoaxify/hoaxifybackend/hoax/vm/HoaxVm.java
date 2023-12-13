package com.hoaxify.hoaxifybackend.hoax.vm;

import com.hoaxify.hoaxifybackend.file.FileAttachment;
import com.hoaxify.hoaxifybackend.file.vm.FileAttachmentVm;
import com.hoaxify.hoaxifybackend.hoax.Hoax;
import com.hoaxify.hoaxifybackend.user.User;
import com.hoaxify.hoaxifybackend.user.vm.UserVm;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */
@Data
public class HoaxVm {

    private Long hoaxId;

    private String content;

    private long createdAt;

    private UserVm user;

    private FileAttachmentVm fileAttachment;

    public HoaxVm(Hoax hoax) {
        this.hoaxId = hoax.getHoaxId();
        this.content = hoax.getContent();
        this.createdAt = hoax.getCreatedAt().getTime();
        this.user = new UserVm(hoax.getUser());
        if (hoax.getAttachment() != null)
            this.fileAttachment = new FileAttachmentVm(hoax.getAttachment());
    }
}
