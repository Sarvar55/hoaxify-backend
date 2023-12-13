package com.hoaxify.hoaxifybackend.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.hoaxify.hoaxifybackend.Shared.Views;
import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)//burada bezen her hangi bir degerimiz null ola bilir constructorudakilar haric
//o zaman onlari null donmek istemeyiz iste burada JAkson devreye giricek null olanlari responsda gostermeyecek
public class ApiError {
    @JsonView(Views.Base.class)
    private int status;
    @JsonView(Views.Base.class)
    private String message;
    @JsonView(Views.Base.class)
    private String path;
    @JsonView(Views.Base.class)
    private long timestamp = new Date().getTime();
    private Map<String, String> validationsErrors;

    public ApiError(int status, String message, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
    }
}
