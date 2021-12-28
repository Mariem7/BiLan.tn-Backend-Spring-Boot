package com.CIMF.BiLan.tnBackendSpringBoot.security.jwt;

import com.CIMF.BiLan.tnBackendSpringBoot.security.UserPrincipal;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface JwtProvider {
    //the first step is to create the method of generating a token
    //the UserPrincipal is created after the loggin
    String generateToken(UserPrincipal auth);

    //The second step is to get Authentication
    //we will read the authorization header from HttpServletRequest and then we will create an authorization object
    Authentication getAuthentication(HttpServletRequest request);

    //the last step is to create the validate token method
    boolean isTokenValid(HttpServletRequest request);
}
