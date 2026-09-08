package com.example.FinalYearProject.repository;

import com.example.FinalYearProject.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCompanyNameIgnoreCase(String companyName);
    List<Booking> findByNameIgnoreCase(String name);
    List<Booking> findByStatus(Booking.Status status); // ✅ Only this one

    List<Booking> findByCompanyNameAndStatusAndReviewIsNotNull(String companyName, Booking.Status status);
}
