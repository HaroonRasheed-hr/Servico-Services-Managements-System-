package com.example.FinalYearProject.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "admins")
public class Admin {
   // fields, e.g. id, username, password
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String username;
   private String password;

   public Admin(Long id, String username, String password) {
       this.id = id;
       this.username = username;
       this.password = password;
   }

   // getters and setters
}
