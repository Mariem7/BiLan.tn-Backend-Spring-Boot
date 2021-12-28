package com.CIMF.BiLan.tnBackendSpringBoot.service;

import com.CIMF.BiLan.tnBackendSpringBoot.model.JwtRefreshToken;
import com.CIMF.BiLan.tnBackendSpringBoot.model.User;

public interface JwtRefreshTokenService {

    //our first method is to create a new Refresh Token
    JwtRefreshToken createRefreshToken(Long userId);


    //the second method is to generate an access token from a refresh token
    User generateAccessTokenFromRfreshToken(String refreshTokenId);

}
