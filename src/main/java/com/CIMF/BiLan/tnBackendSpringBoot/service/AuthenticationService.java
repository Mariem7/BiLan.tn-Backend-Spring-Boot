package com.CIMF.BiLan.tnBackendSpringBoot.service;

import com.CIMF.BiLan.tnBackendSpringBoot.model.User;

public interface AuthenticationService {

    User signInAndReturnJWT(User signInRequest);


}
