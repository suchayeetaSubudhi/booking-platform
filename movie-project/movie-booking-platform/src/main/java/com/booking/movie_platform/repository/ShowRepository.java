package com.booking.movie_platform.repository;

import com.booking.movie_platform.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Integer> {
    List<Show> findByMovieIdAndShowDate(Integer movieId, LocalDate showDate);
    List<Show> findByTheatreId(Integer theatreId);
}
