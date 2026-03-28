package com.booking.movie_platform.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.movie_platform.entity.Show;

public interface ShowRepository extends JpaRepository<Show, Integer> {

	List<Show> findByMovieIdAndShowDate(Integer movieId, LocalDate showDate);
}