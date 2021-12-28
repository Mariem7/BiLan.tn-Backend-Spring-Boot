package com.CIMF.BiLan.tnBackendSpringBoot.security;

import com.CIMF.BiLan.tnBackendSpringBoot.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

//we need getters for this class so we can call the lombok annotation
@Getter
//we need also constructors with arguments and no arguments
@NoArgsConstructor
@AllArgsConstructor
//The @Builder annotation produces complex builder APIs for our classe
@Builder
public class UserPrincipal implements UserDetails {

    private Long id;
    private String username;
    //don't show up on serialized place
    transient private String password;
    //user only for the login operation, don't use in JWT
    transient private User user;
    private Set<GrantedAuthority> authorities;

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
}
