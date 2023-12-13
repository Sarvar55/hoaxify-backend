package com.hoaxify.hoaxifybackend.error;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */
@RestController
@RequestMapping("/secured")
public class DummyController {

    @GetMapping("")
    public String secure(){
        return "merhaba ben secure ";
    }
}
