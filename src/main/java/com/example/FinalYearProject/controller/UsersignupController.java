package com.example.FinalYearProject.controller;

import com.example.FinalYearProject.model.Usersignup;
import com.example.FinalYearProject.service.UsersignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UsersignupController {

    @Autowired
    private UsersignupService service;

    @Autowired
    private com.example.FinalYearProject.repository.UsersignupRepository usersignupRepository;

    @PostMapping("/signup")
    public String signupUser(@RequestBody Usersignup user) {
        String result = service.registerUser(user);
        if ("Success".equals(result)) {
            return "Signup successful";
        } else {
            return result;
        }
    }
    @GetMapping("/{username}")
    public ResponseEntity<Usersignup> getUserByUsername(@PathVariable String username) {
        return usersignupRepository.findByUsername(username)
                .map(user -> ResponseEntity.ok(user)) // Return JSON user object
                .orElseGet(() -> ResponseEntity.notFound().build()); // Return 404 if not found
    }
    
    

}
