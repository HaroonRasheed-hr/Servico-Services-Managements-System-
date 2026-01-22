package com.example.FinalYearProject.service;

import com.example.FinalYearProject.model.Admin;
import com.example.FinalYearProject.repository.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner insertDefaultAdmin(AdminRepository adminRepo) {
        return args -> {
            if (adminRepo.findAll().isEmpty()) {
                adminRepo.save(new Admin(null, "admin", "admin123"));
                System.out.println("Default admin inserted.");
            }
        };
    }
}
