package com.booking.movie_platform.controller;

import com.booking.movie_platform.dto.*;
import com.booking.movie_platform.entity.Show;
import com.booking.movie_platform.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/shows")
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;

    // Browse theatres running the movie on a date- /shows?movieId=1&date=2026-04-01

    @GetMapping
    public List<TheatreShowsResponse> getTheatresForMovie(
            @RequestParam Integer movieId,
            @RequestParam String date) {
        return showService.getTheatresForMovie(movieId, LocalDate.parse(date));
    }

    // Platform offers available in a city - /shows/offers?city=Mumbai

    @GetMapping("/offers")
    public List<OfferResponse> getOffers(@RequestParam String city) {
        return showService.getOffersForCity(city);
    }

    // Create shows /shows
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Show createShow(@RequestBody ShowRequest req) {
        return showService.createShow(req);
    }

    // Update a show /shows/{id}
    @PutMapping("/{id}")
    public Show updateShow(@PathVariable Integer id, @RequestBody ShowRequest req) {
        return showService.updateShow(id, req);
    }

    // Delete show /shows/{id}
    @DeleteMapping("/{id}")
    public void deleteShow(@PathVariable Integer id) {
        showService.deleteShow(id);
    }

    // Allocate/update seat inventory - /shows/inventory
    @PutMapping("/inventory")
    public Show updateInventory(@RequestBody SeatInventoryRequest req) {
        return showService.updateSeatInventory(req);
    }
}
