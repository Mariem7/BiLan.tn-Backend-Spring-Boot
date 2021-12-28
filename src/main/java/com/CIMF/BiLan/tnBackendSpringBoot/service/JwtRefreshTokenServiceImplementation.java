package com.CIMF.BiLan.tnBackendSpringBoot.service;

import com.CIMF.BiLan.tnBackendSpringBoot.model.JwtRefreshToken;
import com.CIMF.BiLan.tnBackendSpringBoot.model.User;
import com.CIMF.BiLan.tnBackendSpringBoot.repository.JwtRfreshTokenRepository;
import com.CIMF.BiLan.tnBackendSpringBoot.repository.UserRepository;
import com.CIMF.BiLan.tnBackendSpringBoot.security.UserPrincipal;
import com.CIMF.BiLan.tnBackendSpringBoot.security.jwt.JwtProvider;
import com.CIMF.BiLan.tnBackendSpringBoot.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.UUID;

@Service
public class JwtRefreshTokenServiceImplementation implements JwtRefreshTokenService{

    @Value("${app.jwt.refresh-expiration-in-ms}")
    private Long REFRESH_EXPRIRATION_IN_MS;

    @Autowired
    private JwtRfreshTokenRepository jwtRfreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    //our first method is to create a new Refresh Token
    @Override
    public JwtRefreshToken createRefreshToken(Long userId){
        JwtRefreshToken jwtRefreshToken = new JwtRefreshToken();
        //UUID will provide a random ID
        jwtRefreshToken.setTokenId(UUID.randomUUID().toString());
        jwtRefreshToken.setUserId(userId);
        jwtRefreshToken.setCreateDate(LocalDateTime.now());
        jwtRefreshToken.setExpirationDate(LocalDateTime.now().plus(REFRESH_EXPRIRATION_IN_MS, ChronoUnit.MILLIS));

        return jwtRfreshTokenRepository.save(jwtRefreshToken);
    }

    //the second method is to generate an access token from a refresh token
    @Override
    public User generateAccessTokenFromRfreshToken(String refreshTokenId){
        JwtRefreshToken jwtRefreshToken = jwtRfreshTokenRepository.findById(refreshTokenId).orElseThrow();
        if(jwtRefreshToken.getExpirationDate().isBefore(LocalDateTime.now())){
            throw new RuntimeException("JWT refresh token is not valid");
        }
        User user = userRepository.findById(jwtRefreshToken.getUserId()).orElseThrow();
        //we will convert the user to userPrincipal because we need a userPrincipal to generate a JWT
        UserPrincipal userPrincipal = UserPrincipal.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(Set.of(SecurityUtils.convertToAuthority(user.getRole().name())))
                .build();
        String accessToken = jwtProvider.generateToken(userPrincipal);

        user.setAccessToken(accessToken);
        user.setRefreshToken(refreshTokenId);

        return user;
    }


}
