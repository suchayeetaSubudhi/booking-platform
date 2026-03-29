package com.booking.movie_platform.controller;

import com.booking.movie_platform.dto.*;
import com.booking.movie_platform.entity.Booking;
import com.booking.movie_platform.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    // Create single booking
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse book(@RequestBody BookingRequest req) {
        return bookingService.bookTickets(req);
    }

    // Delete/Cancel a booking /bookings/{id}
    @DeleteMapping("/{id}")
    public Map<String, String> cancel(@PathVariable Integer id) {
        return bookingService.cancelBooking(id);
    }
    
    // Get bookings by user /bookings?userId=suchayeeta
    @GetMapping
    public List<Booking> getByUser(@RequestParam String userId) {
        return bookingService.getBookingsByUser(userId);
    }
}
