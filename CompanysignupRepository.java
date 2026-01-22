package com.example.FinalYearProject.repository;

import com.example.FinalYearProject.model.Companysignup;
import com.example.FinalYearProject.service.CompanysignupService;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CompanysignupRepository extends JpaRepository<Companysignup, Long> {
    List<Companysignup> findByStatus(String status);

    boolean existsByCompanyUsername(String companyUsername);

    Optional<Companysignup> findByCompanyUsernameAndPassword(String username, String password);
    
    Optional<Companysignup> findByCompanyUsername(String companyUsername);
    
}
