package com.hoaxify.hoaxifybackend.user.vm;

import com.hoaxify.hoaxifybackend.Shared.FileType;
import com.hoaxify.hoaxifybackend.user.User;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */
@Data
public class UpdateUserVm {
    @Size(min = 5, max = 15)
    @NotEmpty
    @NotNull
    private String displayName;
    @FileType(types = {"jpeg","png"})
    private String image;

    public UpdateUserVm(User user) {
        this.setDisplayName(user.getDisplayName());
        this.setImage(user.getImage());
    }

    public UpdateUserVm() {

    }
}
