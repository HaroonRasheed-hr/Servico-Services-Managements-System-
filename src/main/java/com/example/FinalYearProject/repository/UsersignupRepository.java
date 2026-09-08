package com.example.FinalYearProject.repository;

import com.example.FinalYearProject.model.Usersignup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersignupRepository extends JpaRepository<Usersignup, Long> {
    boolean existsByUsername(String username);

    // Add this method to support login functionality
   Optional<Usersignup> findByUsernameAndPassword(String username, String password);
    // Add this method to support user registration
    Optional<Usersignup> findByUsername(String username);

}