package com.example.FinalYearProject.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Companysignup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String services;
    private String email;
    private String address;
    private String phone;

    @Column(unique = true)
    private String companyUsername;

    private String password;

    private String status = "Pending"; // Possible: Pending, Approved, Rejected
    
    public String getStatus() {
      return status;
  }
  
  public void setStatus(String status) {
     this.status = status;
  }
  
  public String getEmail() {
   return email;
}

public void setEmail(String email) {
   this.email = email;
}


public String getName() {
   return name;
}
public void setCompanyname(String companyname) {
   this.name = companyname;
}

public String getCompanyname() {
   return this.name;
}

public String getPassword() {
   return password;
}

public void setPassword(String password) {
   this.password = password;
}
public String getCompanyUsername() {
   return this.companyUsername;
}
}
