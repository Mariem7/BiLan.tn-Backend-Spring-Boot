package com.CIMF.BiLan.tnBackendSpringBoot.security.jwt;

import com.CIMF.BiLan.tnBackendSpringBoot.security.UserPrincipal;
import com.CIMF.BiLan.tnBackendSpringBoot.utils.SecurityUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

//this Jwt Provider will be a spring component
@Component
public class JwtProviderImplemtation implements JwtProvider{

    //this is the key
    @Value("${app.jwt.secret}")
    private String JWT_SECRET;

    //this is the expiration duration of the token
    @Value("${app.jwt.expiration-in-ms}")
    private Long JWT_EXPIRATION_IN_MS;

    //the first step is to create the method of generating a token
    //the UserPrincipal is created after the loggin
    @Override
    public String generateToken(UserPrincipal auth){
        String authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        //we can use different key for encryption and decryption
        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(auth.getUsername())
                .claim("roles",authorities)
                .claim("userId",auth.getId())
                .setExpiration(new Date(System.currentTimeMillis() +JWT_EXPIRATION_IN_MS ))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    //The second step is to get Authentication
    //we will read the authorization header from HttpServletRequest and then we will create an authorization object
    @Override
    public Authentication getAuthentication(HttpServletRequest request){
        Claims claims = extractClaims(request);
        if (claims == null){
            return null;
        }
        //then we will return the username from the token
        String username = claims.getSubject();
        Long userId = claims.get("userId", Long.class);

        Set<GrantedAuthority> authorities= Arrays.stream(claims.get("roles").toString().split(","))
                .map(SecurityUtils::convertToAuthority)
                .collect(Collectors.toSet());

        UserDetails userDetails = UserPrincipal.builder()
                .username(username)
                .authorities(authorities)
                .id(userId)
                .build();
        if(username == null){
            return null;
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null,authorities);
    }


    //we will extract the header from the authorization
    private Claims extractClaims(HttpServletRequest request){
        String token = SecurityUtils.extractAuthTokenFromRequest(request);
        if(token == null){
            return null;
        }
        //we need to encrypt the JWT secret
        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();


    }

    //the last step is to create the validate token method
    @Override
    public boolean isTokenValid(HttpServletRequest request){
        Claims claims = extractClaims(request);
        if (claims == null){
            return false;
        }
        if(claims.getExpiration().before(new Date())){
            //that means the token didn't expirate yet
            return false;
        }
        return true;
    }



}
