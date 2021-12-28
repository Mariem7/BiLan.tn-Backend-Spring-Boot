package com.CIMF.BiLan.tnBackendSpringBoot.service;

import com.CIMF.BiLan.tnBackendSpringBoot.model.Role;
import com.CIMF.BiLan.tnBackendSpringBoot.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    //the first method is to save a user
    User saveUser(User user);

    //the second method is findUser by Username
    Optional<User> findByUsername(String username);

    //the third method is change the role of the user
    void changeRole(Role newRole, String username);

    //the last method is findAllUsers
    List<User> findAllUsers();
}
