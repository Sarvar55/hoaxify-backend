package com.hoaxify.hoaxifybackend.file;

import com.hoaxify.hoaxifybackend.hoax.Hoax;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */
@Entity
@Data
public class FileAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private String fileType;

    @OneToOne(cascade = CascadeType.ALL)
    private Hoax hoax;

}
