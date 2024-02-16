package com.example.travel.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


public class CustomUserDetails implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;

    private String avatar;

    public CustomUserDetails(Long id, String username, String password, String email, Collection<? extends GrantedAuthority> authorities, String avatar) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.authorities = authorities;
        this.avatar = avatar;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

    public static CustomUserDetails build(User user) {
        //List<GrantedAuthority> authority = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()));
        List<GrantedAuthority> authority = List.of(new SimpleGrantedAuthority(user.getRole().name()));
        return new CustomUserDetails(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), authority, user.getAvatar());
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }
}
