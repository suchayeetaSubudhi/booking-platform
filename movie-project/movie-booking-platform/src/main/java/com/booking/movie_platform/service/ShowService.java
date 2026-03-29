package com.booking.movie_platform.service;

import com.booking.movie_platform.dto.*;
import com.booking.movie_platform.entity.*;
import com.booking.movie_platform.exception.BookingException;
import com.booking.movie_platform.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShowService {

    private final ShowRepository showRepository;
    private final TheatreRepository theatreRepository;

    // Browse theatres running a movie on a given date

    public List<TheatreShowsResponse> getTheatresForMovie(Integer movieId, LocalDate date) {
        List<Show> shows = showRepository.findByMovieIdAndShowDate(movieId, date);

        Map<Integer, List<Show>> byTheatre = shows.stream()
                .collect(Collectors.groupingBy(Show::getTheatreId));

        return byTheatre.entrySet().stream().map(entry -> {
            Theatre theatre = theatreRepository.findById(entry.getKey())
                    .orElse(null);
            return TheatreShowsResponse.builder()
                    .theatre(theatre)
                    .shows(entry.getValue())
                    .build();
        }).collect(Collectors.toList());
    }

    // Platform offers in selected cities/theatres
    //   - 50% off 3rd ticket
    //   - 20% off afternoon shows (12:00–16:59)

    public List<OfferResponse> getOffersForCity(String city) {
        List<Theatre> theatres = theatreRepository.findByCity(city);
        List<OfferResponse> responses = new ArrayList<>();
        for (Theatre theatre : theatres) {
            List<String> offers = new ArrayList<>();
            offers.add("50% discount on the 3rd ticket");

            List<Show> shows = showRepository.findByTheatreId(theatre.getId());
            boolean hasAfternoon = shows.stream().anyMatch(s -> isAfternoonShow(s.getShowTime()));
            if (hasAfternoon) {
                offers.add("20% discount on afternoon shows (12:00–16:59)");
            }
            responses.add(OfferResponse.builder()
                    .city(city)
                    .theatreName(theatre.getName())
                    .activeOffers(offers)
                    .build());
        }
        return responses;
    }

    // Create a show

    public Show createShow(ShowRequest req) {
        Show show = new Show();
        show.setMovieId(req.getMovieId());
        show.setTheatreId(req.getTheatreId());
        show.setShowTime(req.getShowTime());
        show.setShowDate(req.getShowDate());
        show.setTotalSeats(req.getTotalSeats());
        show.setAvailableSeats(req.getTotalSeats());
        return showRepository.save(show);
    }

    // Update a show

    public Show updateShow(Integer showId, ShowRequest req) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new BookingException("Show not found: " + showId));
        show.setShowTime(req.getShowTime());
        show.setShowDate(req.getShowDate());
        if (req.getTotalSeats() != null) {
            int diff = req.getTotalSeats() - show.getTotalSeats();
            show.setTotalSeats(req.getTotalSeats());
            show.setAvailableSeats(Math.max(0, show.getAvailableSeats() + diff));
        }
        return showRepository.save(show);
    }

    // Delete a show

    public void deleteShow(Integer showId) {
        if (!showRepository.existsById(showId)) {
            throw new BookingException("Show not found: " + showId);
        }
        showRepository.deleteById(showId);
    }

    // Allocate / update seat inventory for a show

    public Show updateSeatInventory(SeatInventoryRequest req) {
        Show show = showRepository.findById(req.getShowId())
                .orElseThrow(() -> new BookingException("Show not found: " + req.getShowId()));
        int booked = show.getTotalSeats() - show.getAvailableSeats();
        if (req.getTotalSeats() < booked) {
            throw new BookingException("Cannot reduce seats below already-booked count: " + booked);
        }
        show.setTotalSeats(req.getTotalSeats());
        show.setAvailableSeats(req.getTotalSeats() - booked);
        return showRepository.save(show);
    }

    public static boolean isAfternoonShow(String showTime) {
        if (showTime == null) return false;
        String[] parts = showTime.split(":");
        int hour = Integer.parseInt(parts[0]);
        return hour >= 12 && hour < 17;
    }
}
