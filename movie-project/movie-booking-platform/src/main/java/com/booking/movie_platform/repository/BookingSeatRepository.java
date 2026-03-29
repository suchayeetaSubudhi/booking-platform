package com.booking.movie_platform.repository;

import com.booking.movie_platform.entity.BookingSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingSeatRepository extends JpaRepository<BookingSeat, Integer> {
    List<BookingSeat> findByBookingId(Integer bookingId);
}
