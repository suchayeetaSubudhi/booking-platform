package com.booking.movie_platform.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.movie_platform.entity.Show;
import com.booking.movie_platform.repository.ShowRepository;

@Service
public class ShowService {

	@Autowired
	private ShowRepository showRepository;

	public List<Show> getShows(Integer movieId, LocalDate date) {
		return showRepository.findByMovieIdAndShowDate(movieId, date);
	}
}