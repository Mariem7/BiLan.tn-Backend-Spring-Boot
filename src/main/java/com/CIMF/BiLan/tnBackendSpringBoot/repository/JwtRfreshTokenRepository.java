package com.CIMF.BiLan.tnBackendSpringBoot.repository;

import com.CIMF.BiLan.tnBackendSpringBoot.model.JwtRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtRfreshTokenRepository extends JpaRepository <JwtRefreshToken, String>{

}
