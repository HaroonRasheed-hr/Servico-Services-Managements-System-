package com.example.FinalYearProject.controller;

import com.example.FinalYearProject.model.Usersignup;
import com.example.FinalYearProject.service.UserloginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import java.util.Map;

import java.util.Optional;

@Controller
@RequestMapping("/api/users")
public class UserloginController {

    @Autowired
    private UserloginService loginService;

    // Login handler with session
   @PostMapping("/login")
@ResponseBody
public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password, HttpSession session) {
    // Debug log
    System.out.println("Login request for: " + username);

    Optional<Usersignup> user = loginService.login(username, password);
    
    if (user.isPresent()) {
        session.setAttribute("loggedInUser", user.get().getUsername());
        return ResponseEntity.ok("success");
    } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("fail");
    }
}

// Login handler without session


    // Logout handler
    @GetMapping("/logout")
    @ResponseBody
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("loggedOut");
    }

    // Get current logged-in user
    @GetMapping("/current-user")
    @ResponseBody
    public ResponseEntity<String> currentUser(HttpSession session) {
        Object user = session.getAttribute("loggedInUser");
        return user != null ? ResponseEntity.ok(user.toString()) : ResponseEntity.noContent().build();
    }
}
