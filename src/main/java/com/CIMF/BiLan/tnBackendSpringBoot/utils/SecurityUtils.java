package com.CIMF.BiLan.tnBackendSpringBoot.utils;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class SecurityUtils {

    //we need to set a prefix if it doesn' exist
    public static final String ROLE_PREFIX="ROLE_";

    public static final String AUTH_HEADER = "authorization";
    public static final String AUTH_TOKEN_HEADER ="Bearer";
    public static final String AUTH_TOKEN_PREFIX = AUTH_TOKEN_HEADER + " ";

    public static SimpleGrantedAuthority convertToAuthority(String role){
        String formattedRole = role.startsWith(ROLE_PREFIX) ? role : ROLE_PREFIX +role;
        return new SimpleGrantedAuthority(formattedRole);
    }

    public static String extractAuthTokenFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader(AUTH_HEADER);
        if(StringUtils.hasLength(bearerToken) && bearerToken.startsWith(AUTH_TOKEN_PREFIX)){
            //we will reach our token after 7 letters (6 for the word : bearer and 1 fro the space between them)
            return bearerToken.substring(7);
        }
        return null;
    }



}
