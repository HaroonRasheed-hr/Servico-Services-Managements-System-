package com.example.FinalYearProject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Booking {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String name;
   private String email;
   private String phone;
   private String address;

   public String getPhone() {
      return phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   private String serviceName;
   private String companyName;
   private String companyUsername; // ✅ Add this line


   private String bookingDate;
   private String bookingTime;
   private String serviceType;
   private String serviceLocation;

   @Enumerated(EnumType.STRING)
   private Status status = Status.PENDING;

   public enum Status {
      PENDING,
      APPROVED,
      REJECTED,
      COMPLETED
   }

   public void setStatus(Status status) {
      this.status = status;
   }

   // Getter for status (optional, but recommended)
   public Status getStatus() {
      return status;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getServiceName() {
      return serviceName;
  }
  
  public void setServiceName(String serviceName) {
     this.serviceName = serviceName;
  }

  public String getCompanyName() {
     return this.companyName;
  }


  private Integer rating;

  public Integer getRating() {
      return rating;
  }

  public void setRating(Integer rating) {
     this.rating = rating;
  }
  
  private String review;

  // existing methods

  public String getReview() {
      return review;
  }

  public void setReview(String review) {
      this.review = review;
  }

}