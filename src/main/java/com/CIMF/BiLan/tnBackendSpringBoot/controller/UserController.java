package com.CIMF.BiLan.tnBackendSpringBoot.controller;

import com.CIMF.BiLan.tnBackendSpringBoot.model.Role;
import com.CIMF.BiLan.tnBackendSpringBoot.security.UserPrincipal;
import com.CIMF.BiLan.tnBackendSpringBoot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    //we need to access to the authenticated user
    //with @AuthenticationPrincipal we can reach to the authenticated user
    //the complete path is api/user/change/{role}
    @PutMapping("change/{role}")
    public ResponseEntity<?> changeRole(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Role role){
        userService.changeRole(role, userPrincipal.getUsername());
        return ResponseEntity.ok(true);
    }


}
