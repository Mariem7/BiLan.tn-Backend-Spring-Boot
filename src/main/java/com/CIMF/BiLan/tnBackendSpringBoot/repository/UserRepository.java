package com.CIMF.BiLan.tnBackendSpringBoot.repository;

import com.CIMF.BiLan.tnBackendSpringBoot.model.Role;
import com.CIMF.BiLan.tnBackendSpringBoot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

//the UserRepository will extend from the JpaRepository
//JpaRepository handle many methods like FindAll(), FindById(),...
// we need to provide the model class and the type of the ID which is Long in our case
public interface UserRepository extends JpaRepository<User, Long> {

    //we can create a select query based on our attribute in our model class
    //the username
    Optional<User> findByUsername(String username);

    //we need to specify this query as a modifying query (the result of this query will change the database table)
    @Modifying
    //we will create an update query
    @Query("update User set role =:role where username =:username")
    void updateUseRole(@Param("username") String username, @Param("role")Role role);


}
