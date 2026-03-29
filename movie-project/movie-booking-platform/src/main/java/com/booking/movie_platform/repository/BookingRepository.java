package com.booking.movie_platform.repository;

import com.booking.movie_platform.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByUserId(String userId);
    List<Booking> findByShowId(Integer showId);
    long countByShowIdAndStatus(Integer showId, String status);
}
