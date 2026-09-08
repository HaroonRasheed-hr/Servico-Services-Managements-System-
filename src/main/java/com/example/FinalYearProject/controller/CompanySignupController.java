package com.example.FinalYearProject.controller;

import com.example.FinalYearProject.model.Companysignup;
import com.example.FinalYearProject.service.CompanysignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api") // ✅ change this from "/api/admin"
@CrossOrigin(origins = "*")
public class CompanySignupController {

    @Autowired
    private CompanysignupService service;

    // ✅ Company Signup Endpoint
    @PostMapping("/company/signup")
    public ResponseEntity<String> signupCompany(@RequestBody Companysignup company) {
        boolean exists = service.existsByCompanyUsername(company.getCompanyUsername());
        if (exists) {
            return ResponseEntity.badRequest().body("Username already exists. Please choose another.");
        }

        service.saveCompany(company);
        return ResponseEntity.ok("Company signup successful. Wait for approval from SERVICO admin.");
    }

    // ✅ Admin APIs
    @GetMapping("/admin/companies")
    public List<Companysignup> getAllCompanies(@RequestParam(required = false) String status) {
        if (status != null && !status.equalsIgnoreCase("All")) {
            return service.getCompaniesByStatus(status);
        } else {
            return service.getAllCompanies();
        }
    }

    @PutMapping("/admin/companies/{id}/status")
    public String updateCompanyStatus(@PathVariable Long id, @RequestParam String status) {
        service.updateStatusAndNotify(id, status);
        return "Company status updated to: " + status;
    }

@GetMapping("/{username}")
public ResponseEntity<Companysignup> getCompanyByUsername(@PathVariable String username) {
    Optional<Companysignup> company = service.getCompanyByUsername(username);
    return company.map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
}


}
