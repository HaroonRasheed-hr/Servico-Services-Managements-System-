package com.example.FinalYearProject.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "services")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceName;
    private String description;

    private double avgPrice;
    private double premiumPrice;
    private double vipPrice;

    private String openTime;
    private String closeTime;

    private String companyName; // companyUsername from localStorage

    public String getCompanyName() {
        return companyName;
    }
    
}

