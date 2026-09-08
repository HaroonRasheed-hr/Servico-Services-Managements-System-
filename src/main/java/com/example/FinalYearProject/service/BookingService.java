package com.example.FinalYearProject.service;

import com.example.FinalYearProject.model.Booking;
import com.example.FinalYearProject.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private BookingRepository repo;

    @Autowired
    private JavaMailSender mailSender;  // configure SMTP in application.properties

    public Booking createBooking(Booking booking) {
        if (booking.getEmail() == null) booking.setEmail("user@example.com");
        booking.setStatus(Booking.Status.PENDING);
        return repo.save(booking);
    }

    public List<Booking> getBookingsByCompany(String company) {
        return repo.findByCompanyNameIgnoreCase(company);
    }
    
     

    public List<Booking> getBookingsByUser(String name) {
        return repo.findByNameIgnoreCase(name); // ✅ new
     }
     
  

     public Booking updateStatus(Long id, Booking.Status status) {
         Optional<Booking> opt = repo.findById(id);
         if (opt.isPresent()) {
             Booking b = opt.get();
             b.setStatus(status);
             Booking updated = repo.save(b);
             System.out.println("Service Booking: " + b);

             // send email to user
             SimpleMailMessage msg = new SimpleMailMessage();
             msg.setTo(b.getEmail());
             msg.setSubject("Booking " + status);
             msg.setText(b.getCompanyName() + " " + status + " your service booking for " + b.getServiceName());
             mailSender.send(msg);

             return updated;

         }
         throw new RuntimeException("Booking not found");
     }
     public List<Booking> getReviewedBookingsByCompanyUsername(String username) {
        return repo.findByCompanyNameAndStatusAndReviewIsNotNull(username, Booking.Status.COMPLETED);
    }
    
}
