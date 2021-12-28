package com.CIMF.BiLan.tnBackendSpringBoot.security;

import com.CIMF.BiLan.tnBackendSpringBoot.model.User;
import com.CIMF.BiLan.tnBackendSpringBoot.service.UserService;
import com.CIMF.BiLan.tnBackendSpringBoot.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

//this class will be a service that provide us with details of the user from the user service
@Service
public class CustomUserDetailsService implements UserDetailsService {

    //we need to inject the userService
    @Autowired
    private UserService userService;

    @Override
    //in this method we are going to find our user according to the username from our user service
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //if the user doesn't exist then an exception will be thrown (username not found)
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username:" + username));

        //we will set the authority of spring boot security from the role of our user
        Set<GrantedAuthority> authorities = Set.of(SecurityUtils.convertToAuthority(user.getRole().name()));

        //this is a pattern of builder annotation
        return UserPrincipal.builder()
                .user(user)
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}
