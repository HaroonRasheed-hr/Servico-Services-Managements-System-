package com.example.FinalYearProject.service;

import com.example.FinalYearProject.model.ServiceEntity;
import com.example.FinalYearProject.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceService {

    @Autowired
    private ServiceRepository repository;

    public ServiceEntity saveService(ServiceEntity service) {
        return repository.save(service);
    }

    public List<ServiceEntity> getAllServices() {
        return repository.findAll();
    }

    public List<ServiceEntity> getByCompanyName(String companyName) {
        return repository.findByCompanyName(companyName);
    }
    public List<ServiceEntity> getServicesByCompanyUsername(String username) {
        return repository.findByCompanyName(username);
    }
    
}
