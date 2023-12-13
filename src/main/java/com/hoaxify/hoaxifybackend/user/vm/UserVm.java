package com.hoaxify.hoaxifybackend.user.vm;

import com.hoaxify.hoaxifybackend.user.User;
import lombok.Data;

/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */
@Data
public class UserVm {
    private String userName;
    private String displayName;
    private String image;

    public UserVm(User user){
        setUserName(user.getUsername());
        setDisplayName(user.getDisplayName());
        setImage(user.getImage());
    }
}
