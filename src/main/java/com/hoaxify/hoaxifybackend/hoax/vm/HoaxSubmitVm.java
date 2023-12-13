package com.hoaxify.hoaxifybackend.hoax.vm;

import lombok.Data;

import javax.validation.constraints.Size;

/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */
@Data
public class HoaxSubmitVm {
    @Size(min = 1, max = 1000)
    private String content;

    private Long attachmentId;
}
