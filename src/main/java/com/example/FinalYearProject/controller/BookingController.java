package com.example.FinalYearProject.controller;

import com.example.FinalYearProject.model.Booking;
import com.example.FinalYearProject.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired
    private BookingService service;

    @Autowired
    private com.example.FinalYearProject.repository.BookingRepository bookingRepository;

    // === Create Booking ===
    @PostMapping("/create")
    public String createBooking(@RequestBody Booking booking) {
        service.createBooking(booking);
        return "Booking confirmed! Wait for company approval.";
    }

    // === Get Bookings by Company ===
    @GetMapping("/company/{companyUsername}")
    public List<Booking> getByCompany(@PathVariable String companyUsername) {
        return service.getBookingsByCompany(companyUsername);
    }

    // === Get Bookings by User ===
    @GetMapping("/user/{username}")
    public List<Booking> getByUser(@PathVariable String username) {
        return service.getBookingsByUser(username);
    }

    // === Update Booking Status ===
    @PutMapping("/status/{id}")
    public Booking updateStatus(@PathVariable Long id, @RequestParam Booking.Status status) {
        return service.updateStatus(id, status);
    }

    // === Submit Review ===
    @PutMapping("/review/{id}")
    public ResponseEntity<?> submitReview(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isPresent()) {
            Integer rating = (Integer) body.get("rating");
            String review = (String) body.get("review");

            if (rating != null && review != null) {
                booking.get().setRating(rating);
                booking.get().setReview(review);
                bookingRepository.save(booking.get());
                return ResponseEntity.ok("Review submitted");
            } else {
                return ResponseEntity.badRequest().body("Rating or review missing");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found");
    }

    // === Get All Reviews (Clean Format) ===
    @GetMapping("/reviews/all")
    public List<Map<String, Object>> getAllReviews() {
        List<Booking> completed = bookingRepository.findByStatus(Booking.Status.COMPLETED); // ✅ Fix here
        return completed.stream()
                .filter(b -> b.getReview() != null && b.getRating() != null)
                .map(b -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("serviceName", b.getServiceName());
                    m.put("companyName", b.getCompanyName());
                    m.put("rating", b.getRating());
                    m.put("review", b.getReview());
                    return m;
                }).collect(Collectors.toList());
    }
    

    // === Get All Completed Bookings (Raw Data) ===
    @GetMapping("/reviews/full")
    public List<Booking> getAllReviewBookings() {
        return bookingRepository.findByStatus(Booking.Status.COMPLETED);
    }
    @GetMapping("/reviews/company/{username}")
public List<Booking> getCompanyReviews(@PathVariable String username) {
    return service.getReviewedBookingsByCompanyUsername(username);
}

}
