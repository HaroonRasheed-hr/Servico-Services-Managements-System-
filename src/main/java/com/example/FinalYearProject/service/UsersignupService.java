package com.example.FinalYearProject.service;

import com.example.FinalYearProject.model.Usersignup;
import com.example.FinalYearProject.repository.UsersignupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersignupService {

    @Autowired
    private UsersignupRepository repository;

    public String registerUser(Usersignup user) {
        if (repository.existsByUsername(user.getUsername())) {
            return "Username already exists";
        }

        repository.save(user);
        return "Success";
    }

    public Usersignup findByUsername(String username) {
        // TODO: Implement actual lookup logic, e.g., from a repository or database
        return null;
    }
    
}
