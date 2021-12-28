package com.CIMF.BiLan.tnBackendSpringBoot.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

//@Data annotation from Lumbok will provide us with getters, setters, equals and hashcode methods
@Data
//means that this class is a JPA entity
@Entity
//means that this is a database table
@Table(name="users")
public class User {

    //the id will be the primary key of our table
    @Id
    //this id will be generated automatically by the auto-incremented database column
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    //the username which will be the email
    @Column(name ="username",unique = true, nullable = false, length = 100)
    private String username;

    @Column(name ="password", nullable = false)
    private String password;

    @Column (name ="firstname",nullable = false)
    private String firstName;

    @Column (name ="lastname", nullable = false)
    private String lastName;

    //the function of the user {function in the society: accountant or CEO}
    @Column (name ="function",nullable = false)
    private String function;

    //the Phone number
    @Column (name ="phoneNumber", nullable = false ,length = 15)
    private String phoneNumber;

    //the National Identity Card Number
    @Column (name = "nicNumber", nullable = false , length=10)
    private String nicNumber;

    //the time of sign up
    @Column(name="sign_up_time", nullable = false)
    private LocalDateTime createTime;

    //the role of the user {user or Admin}
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    //this would not be a presistent field in the database
    @Transient
    private String accessToken;

    @Transient
    private String refreshToken;


}
