package com.CIMF.BiLan.tnBackendSpringBoot.service;

import com.CIMF.BiLan.tnBackendSpringBoot.model.Role;
import com.CIMF.BiLan.tnBackendSpringBoot.model.User;
import com.CIMF.BiLan.tnBackendSpringBoot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

//to define UserServiceImplementation as a service class
@Service
public class UserServiceImplementation implements UserService{

    //we need to call the userRepository
    //so here we have to use dependency injection to inject the userRepository inside this service
    //we add this annotation to highlight the dependency inject
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //we are going create an interface method and to pull it to the UserService Interface
    @Override
    //the first method is to save a user
    public User saveUser(User user){
        //here we will encode our password before saving the user (security stuff for the password)
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //we also will set the default role of the user which is user
        user.setRole(Role.USER);
        //we will set the creation time for the user
        user.setCreateTime(LocalDateTime.now());
        // finally, we will call the save method from the userRepository
        return userRepository.save(user);
    }

    //we are going create an interface method and to pull it to the UserService Interface
    @Override
    //the second method is findUser by Username
    public Optional <User> findByUsername(String username){
        // we will call the findByUsername method from the userRepository
        return userRepository.findByUsername(username);
    }

    //we are going create an interface method and to pull it to the UserService Interface
    @Override
    //the third method is change the role of the user
    //@Transactional annotation is required when we execute an update or delete query
    @Transactional
    public void changeRole(Role newRole, String username){
        userRepository.updateUseRole(username,newRole);
    }

    //we are going create an interface method and to pull it to the UserService Interface
    @Override
    //the last method is findAllUsers
    public List<User>findAllUsers(){
        // we will call the findAll method from the userRepository
        return userRepository.findAll();
    }

}
