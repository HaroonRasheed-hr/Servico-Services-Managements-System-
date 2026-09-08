package com.example.FinalYearProject.service;

import com.example.FinalYearProject.model.Companysignup;
import com.example.FinalYearProject.repository.CompanysignupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanysignupService {

    @Autowired
    private CompanysignupRepository repository;

    @Autowired
    private JavaMailSender mailSender;

    // Fetch all companies
    public List<Companysignup> getAllCompanies() {
        return repository.findAll();
    }

    // Fetch companies by status
    public List<Companysignup> getCompaniesByStatus(String status) {
        return repository.findByStatus(status);
    }

    // Update company status and notify by email
    public void updateStatusAndNotify(Long id, String status) {
        Optional<Companysignup> optionalCompany = repository.findById(id);
        if (optionalCompany.isPresent()) {
            Companysignup company = optionalCompany.get();
            company.setStatus(status);
            repository.save(company);

            sendStatusEmail(company.getEmail(), company.getName(), status);
        }
    }

    // Email logic
    private void sendStatusEmail(String toEmail, String companyName, String status) {
        String subject = "SERVICO Company Registration Status";
        String body;

        switch (status.toLowerCase()) {
            case "approved":
                body = "Dear " + companyName +
                        ",\n\nYour company has been APPROVED on SERVICO. You can now log in and offer your services.\n\nBest regards,\nSERVICO Admin";
                break;
            case "rejected":
                body = "Dear " + companyName +
                        ",\n\nWe regret to inform you that your company signup has been REJECTED.\n\nFor more details, contact support.\n\nRegards,\nSERVICO Admin";
                break;
            default:
                body = "Dear " + companyName + ",\n\nYour company status has been updated to: " + status
                        + "\n\nRegards,\nSERVICO Admin";
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    // Check if username exists
    public boolean existsByCompanyUsername(String username) {
        return repository.existsByCompanyUsername(username);
    }

    // Save a new company
    public void saveCompany(Companysignup company) {
        repository.save(company);
    }

    // Login check for company
    public Optional<Companysignup> login(String username, String password) {
        return repository.findByCompanyUsernameAndPassword(username, password);
    }

    public Optional<Companysignup> getCompanyByUsername(String username) {
        return repository.findByCompanyUsername(username);
    }
    
}
