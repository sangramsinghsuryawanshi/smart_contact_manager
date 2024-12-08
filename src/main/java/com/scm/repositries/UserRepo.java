package com.scm.repositries;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scm.model.Users;

public interface UserRepo extends JpaRepository<Users, String> {
    // Update method to use 'userEmail' instead of 'email'
    public Optional<Users> findByUserEmail(String userEmail);

    // Update method to use 'userEmail' instead of 'email'
    public Optional<Users> findByUserEmailAndPassword(String userEmail, String password);
}
