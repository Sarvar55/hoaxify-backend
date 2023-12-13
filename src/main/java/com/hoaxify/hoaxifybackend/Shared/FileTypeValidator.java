package com.hoaxify.hoaxifybackend.Shared;

import com.hoaxify.hoaxifybackend.file.FileService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */
@Slf4j
@Service
public class FileTypeValidator implements ConstraintValidator<FileType, String> {

    private final FileService fileService;
    private String[] types;

    public FileTypeValidator(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public void initialize(FileType constraintAnnotation) {
        types = constraintAnnotation.types();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        log.info("Gelen image turu: " + value);
        if (value == null || value.isEmpty())
            return true;

        String fileType = fileService.detectType(value);
        log.info(fileType);
        for (String sportedType : types) {
            if (fileType.contains(sportedType))//png png jpeg
                return true;
        }

        context.disableDefaultConstraintViolation();
        HibernateConstraintValidatorContext hibernateContext = context.unwrap(
                HibernateConstraintValidatorContext.class
        );

        String message = Arrays.stream(types).collect(Collectors.joining(", "));
        hibernateContext.addMessageParameter("types", message);
        hibernateContext.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addConstraintViolation();//custom mesaj yazmadik icin
        return false;
    }
}
