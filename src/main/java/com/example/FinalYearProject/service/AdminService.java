package com.example.FinalYearProject.service;

import com.example.FinalYearProject.model.Admin;
import com.example.FinalYearProject.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository repository;

    public boolean validateLogin(String username, String password) {
        Optional<Admin> admin = repository.findByUsernameAndPassword(username, password);
        return admin.isPresent();
    }
}
