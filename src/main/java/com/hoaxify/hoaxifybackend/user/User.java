package com.hoaxify.hoaxifybackend.user;

import com.fasterxml.jackson.annotation.JsonView;
import com.hoaxify.hoaxifybackend.Shared.UniqueUsername;
import com.hoaxify.hoaxifybackend.Shared.Views;
import com.hoaxify.hoaxifybackend.auth.Token;
import com.hoaxify.hoaxifybackend.hoax.Hoax;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;
import java.util.Set;


/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */
@Data
@Entity
@Table(name = "users")
@ToString
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull(message = "{hoax.validation.constraints.NotNull.message}")
    @Size(min = 4, max = 25)
    @UniqueUsername
    @JsonView(Views.Base.class)
    private String userName;

    @Size(min = 4, max = 25)
    @NotNull
    @JsonView(Views.Base.class)
    private String displayName;

    @NotEmpty
    @NotNull
    @Size(min = 8, max = 30)
    //@JsonIgnore
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "{hoax.validation.constraints.Pattern.message}")
    private String password;
    @JsonView(Views.Base.class)

    private String image;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Hoax> hoaxes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Token> tokens;

    public User(String userName, String displayName, String password) {
        this.userName = userName;
        this.displayName = displayName;
        this.password = password;
    }

    public User() {
        System.out.println("ilk burdan calistim");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
    }

    @Override
    public String getUsername() {

        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
