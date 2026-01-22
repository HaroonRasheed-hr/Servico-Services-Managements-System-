package com.example.FinalYearProject.service;

import com.example.FinalYearProject.model.Usersignup;
import com.example.FinalYearProject.repository.UsersignupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserloginService {

    @Autowired
    private UsersignupRepository usersignupRepository;

    public Optional<Usersignup> login(String username, String password) {
        return usersignupRepository.findByUsernameAndPassword(username, password);
    }

    public boolean authenticateUser(String username, String password) {
        return usersignupRepository.findByUsernameAndPassword(username, password).isPresent();
    }
}


