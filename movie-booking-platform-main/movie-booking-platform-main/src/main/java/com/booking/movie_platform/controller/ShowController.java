package com.booking.movie_platform.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.booking.movie_platform.entity.Show;
import com.booking.movie_platform.service.ShowService;

@RestController
@RequestMapping("/shows")
public class ShowController {

	@Autowired
	private ShowService showService;

	@GetMapping
	public List<Show> getShows(@RequestParam Integer movieId, @RequestParam String date) {
		return showService.getShows(movieId, LocalDate.parse(date));
	}
}