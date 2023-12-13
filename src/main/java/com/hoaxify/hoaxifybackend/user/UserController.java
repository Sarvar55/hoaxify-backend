package com.hoaxify.hoaxifybackend.user;

import com.hoaxify.hoaxifybackend.Shared.CurrentUser;
import com.hoaxify.hoaxifybackend.Shared.GenericResponse;
import com.hoaxify.hoaxifybackend.user.vm.UpdateUserVm;
import com.hoaxify.hoaxifybackend.user.vm.UserVm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */

@RestController
@RequestMapping("/api/1.0/")
@Slf4j
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("users")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        GenericResponse response = new GenericResponse("creted", HttpStatus.CREATED);
        userService.save(user);
        return ResponseEntity.ok(response);
    }

    @GetMapping("users")
//burada JSONView kullandim cunki User clasim UserDetail den implement olmus onun ozelliklerinide de bana donuyor
    // bunu istemedigim den dolayi Jsonviewile isaretledim ve bu anatasyon ile de cevap donerken hangilerin iseretlenmisse ona bakip cevap olusturur
    //@JsonView(Views.Base.class)
    //@RequestParam(required = false,defaultValue = "5") Integer currentPage,@RequestParam(required = false,defaultValue = "5"
    // bu sekilde yazdmgimizdsa gozden kacan bir cok sey var size - deger girilmesi ya da daha buyuk bir rakam girilmesi gibi durumlar
    // bunu icin se cozum yolu olarak sprgin  bize Pagable olarak aramatre verin geri kalanin ben hall ederim diyor
    //ama bunu icin pagablenin parametre adlarini tanitmamiz gerekir yoksa hic oraya bakmaz
    public ResponseEntity<Page<UserVm>> getUsers(Pageable pageable, @CurrentUser User user) {
        return ResponseEntity.ok(userService.getUsers(pageable, user).map(UserVm::new));
    }

    @GetMapping("users/{userName}")
    public ResponseEntity<UserVm> getUser(@PathVariable String userName) {
        User user = userService.getUser(userName).get();
        return ResponseEntity.ok(new UserVm(user));
    }

    @PutMapping("users/{userName}")
    @PreAuthorize("#userName==principal.username")
//burasi benim asagidaki yaptigim islemi yapar methoda girmeden once burasi calisir
    //eger true ise o zaman girer false ise 403 atar
    public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserVm updateUserVm, @PathVariable String userName
    ) {
//        if (!loggedInUser.getUsername().equals(userName)) {
//            ApiError error = new ApiError(403, "Forbiden", "/api/1.0/users/" + userName);
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
//        }
        return ResponseEntity.ok(new UserVm(userService.updatedUser(updateUserVm, userName)));
    }

    @DeleteMapping("users/{userName}")
    @PreAuthorize("#userName.equals(principal.username)")
    public ResponseEntity<GenericResponse> deleteUser(@PathVariable String userName) {
        log.info("buraya kadar geldim ");
        userService.deleteUser(userName);
        log.info("buraya kadar geldim artik gernerik response deyicem");

        return ResponseEntity.ok(new GenericResponse("user Deleted"));
    }


}
