package com.CIMF.BiLan.tnBackendSpringBoot.controller;

import com.CIMF.BiLan.tnBackendSpringBoot.model.User;
import com.CIMF.BiLan.tnBackendSpringBoot.service.AuthenticationService;
import com.CIMF.BiLan.tnBackendSpringBoot.service.JwtRefreshTokenService;
import com.CIMF.BiLan.tnBackendSpringBoot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//this is a pre-path
@RequestMapping("api/authentication")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtRefreshTokenService jwtRefreshTokenService;

    //the complete path will be api/authentication/sign-up
    //The Sign Up method
    //the POST API
    @PostMapping("sign-up")
    public ResponseEntity<?>singUp(@RequestBody User user){
        //if the username aleardy exist we will send a conflict response
        if(userService.findByUsername(user.getUsername()).isPresent()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        //else we will save the user
        return new ResponseEntity<>(userService.saveUser(user),HttpStatus.CREATED);
    }
    //the complete path will be api/authentication/sign-in
    //The Sign In method
    @PostMapping("sign-in")
    public ResponseEntity<?>SignIn(@RequestBody User user){
        return new ResponseEntity<>(authenticationService.signInAndReturnJWT(user), HttpStatus.OK);
    }

    //the complete path will be api/authentication/refresh-token?token=
    @PostMapping("refresh-token")
    public ResponseEntity<?> refreshToken(@RequestParam String token){
        return ResponseEntity.ok(jwtRefreshTokenService.generateAccessTokenFromRfreshToken(token));
    }




}
