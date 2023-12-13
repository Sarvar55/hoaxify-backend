package com.hoaxify.hoaxifybackend.user;

import com.hoaxify.hoaxifybackend.error.NotFoundException;
import com.hoaxify.hoaxifybackend.file.FileService;
import com.hoaxify.hoaxifybackend.hoax.HoaxService;
import com.hoaxify.hoaxifybackend.user.vm.UpdateUserVm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Optional;

/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */
@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;

    public UserService(UserRepository userRepository, FileService fileService) {
        this.userRepository = userRepository;
        this.fileService = fileService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Page<User> getUsers(Pageable page, User user) {
        System.out.println("bu xarabaya girirsen");
        if (user != null) {
            System.out.println("burda da girde nolare eee");
            //cunki buraya sadece login olan user istek atamaz diye bir sey demedik yani principalda ver olmaya da biler
            return userRepository.findByUserNameNot(user.getUsername(), page);
        }
        return userRepository.findAll(page);
    }

    public Optional<User> getUser(String userName) {
        return Optional.ofNullable(userRepository.findByUserName(userName)
                .orElseThrow(() -> new NotFoundException("User Not Found")));
    }


    public User updatedUser(UpdateUserVm updateUserVm, String userName) {
        Optional<User> user = getUser(userName);
        user.get().setDisplayName(updateUserVm.getDisplayName());
        if (updateUserVm.getImage() != null) {
            //user.get().setImage(updateUserVm.getImage());
            String oldImageName = user.get().getImage();

            try {
                String storedFile = fileService.writeBase64EncodedStringToFile(updateUserVm.getImage());
                user.get().setImage(storedFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            fileService.deleteProfileImage(oldImageName);
        }
        User updatedUser = userRepository.save(user.get());
        return updatedUser;
    }


    public void deleteUser(String userName) {
        User inDB = userRepository.findByUserName(userName).get();
           fileService.removeAllStorageFilesForUser(inDB);
//        User user = userRepository.findByUserName(userName).get();
        userRepository.delete(inDB);
    }

}
