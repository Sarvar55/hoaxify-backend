package com.hoaxify.hoaxifybackend.hoax;

import com.hoaxify.hoaxifybackend.file.FileAttachment;
import com.hoaxify.hoaxifybackend.user.User;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */
@Entity
@Table(name = "hoaxes")
@Data
public class Hoax {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hoaxId;


    @Column(length = 1000)
    private String content;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @ManyToOne
    private User user;

    @OneToOne(mappedBy = "hoax",cascade = CascadeType.REMOVE)
    private FileAttachment attachment;
}
