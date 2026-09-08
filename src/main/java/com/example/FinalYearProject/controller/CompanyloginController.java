package com.example.FinalYearProject.controller;

import com.example.FinalYearProject.model.Companysignup;
import com.example.FinalYearProject.service.CompanysignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/company")
public class CompanyloginController {

    @Autowired
    private CompanysignupService service;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Companysignup request) {
        return service.login(request.getCompanyUsername(), request.getPassword())
                .map(company -> {
                    String status = company.getStatus();
                    if ("Approved".equalsIgnoreCase(status)) {
                        return ResponseEntity.ok("Login successful");
                    } else if ("Pending".equalsIgnoreCase(status)) {
                        return ResponseEntity.status(403).body("Your account is pending approval.");
                    } else {
                        return ResponseEntity.status(403).body("Your account has been rejected.");
                    }
                })
                .orElse(ResponseEntity.status(401).body("Invalid credentials"));
    }
}
