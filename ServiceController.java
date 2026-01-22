package com.example.FinalYearProject.controller;

import com.example.FinalYearProject.model.ServiceEntity;
import com.example.FinalYearProject.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@CrossOrigin(origins = "*")
public class ServiceController {

    @Autowired
    private ServiceService service;


    @GetMapping("/all") // ✅ Fixed here
    public List<ServiceEntity> getAllServices() {
        return service.getAllServices();
    }
    @PostMapping("/add")
    public String addService(@RequestBody ServiceEntity serviceEntity) {
        System.out.println("Saving service for company: " + serviceEntity.getCompanyName());
        service.saveService(serviceEntity);
        return "Service added successfully";
    }
    @GetMapping("/company/{username}")
    public List<ServiceEntity> getServicesByCompanyUsername(@PathVariable String username) {
        return service.getServicesByCompanyUsername(username);
    }
    
}
