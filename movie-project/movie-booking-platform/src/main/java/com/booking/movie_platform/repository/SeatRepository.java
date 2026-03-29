package com.booking.movie_platform.repository;

import com.booking.movie_platform.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Integer> {
    List<Seat> findByShowId(Integer showId);
    List<Seat> findByShowIdAndIsBooked(Integer showId, Boolean isBooked);
}
