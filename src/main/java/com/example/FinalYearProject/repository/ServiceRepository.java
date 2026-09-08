package com.example.FinalYearProject.repository;

import com.example.FinalYearProject.model.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    List<ServiceEntity> findByCompanyName(String companyName);

}
