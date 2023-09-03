package com.financialtransactions.domain;

import com.financialtransactions.enumerations.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET active = false WHERE id = ?")
@FilterDef(name = "activeUserFilter", parameters = @ParamDef(name = "active", type = Boolean.class))
@Filter(name = "activeUserFilter", condition = "active = :active")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    @NotBlank(message = "O nome do usuário é obrigatório.")
    private String name;
    @NotBlank(message = "O email do usuário é obrigatório.")
    @Column(unique = true)
    @Pattern(regexp = "^(.+)@(.+)$", message = "O email do usuário é inválido.")
    private String email;
    @NotBlank(message = "O login do usuário é obrigatório.")
    @Column(unique = true)
    private String login;
    @NotBlank
    @NotBlank(message = "A senha do usuário é obrigatório.")
    private String password;
    /**
     * This field must be a CPF or CNPJ.
     */
    @NotBlank(message = "O documento do usuário é obrigatório.")
    @Column(unique = true)
    private String document;
    @NotNull(message = "O tipo do usuário é obrigatório.")
    @Enumerated(EnumType.STRING)
    private UserType userType;
    /**
     * This field is used to logically delete the user.
     */
    private Boolean active = Boolean.TRUE;

    // Constructors
    public User() {
    }

    public User(String name, String email, String login, String password, String document, UserType userType) {
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.document = document;
        this.userType = userType;
    }

    // Getters and Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    // Methods from UserDetails interface
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        // todo: implementar a lógica de permissões
//        authorities.add(new SimpleGrantedAuthority(role.getRole().getAuthority()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return getActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return getActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return getActive();
    }

    @Override
    public boolean isEnabled() {
        return getActive();
    }
}
