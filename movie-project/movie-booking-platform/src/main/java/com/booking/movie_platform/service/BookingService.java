package com.booking.movie_platform.service;

import com.booking.movie_platform.dto.*;
import com.booking.movie_platform.entity.*;
import com.booking.movie_platform.exception.BookingException;
import com.booking.movie_platform.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final SeatRepository seatRepository;
    private final ShowRepository showRepository;

    // Book tickets (single booking)
    // Offer logic:
    //   - 50% discount on the 3rd ticket in this booking
    //   - 20% off entire booking for afternoon shows (12:00–16:59)

    @Transactional
    public BookingResponse bookTickets(BookingRequest req) {
        Show show = showRepository.findById(req.getShowId())
                .orElseThrow(() -> new BookingException("Show not found: " + req.getShowId()));

        List<Integer> seatIds = req.getSeatIds();
        if (seatIds == null || seatIds.isEmpty()) {
            throw new BookingException("No seats selected");
        }
        if (show.getAvailableSeats() < seatIds.size()) {
            throw new BookingException("Not enough seats available. Available: " + show.getAvailableSeats());
        }

        // Validate seats
        List<Seat> seats = seatRepository.findAllById(seatIds);
        seats.forEach(seat -> {
            if (!seat.getShowId().equals(req.getShowId())) {
                throw new BookingException("Seat " + seat.getId() + " does not belong to this show");
            }
            if (Boolean.TRUE.equals(seat.getIsBooked())) {
                throw new BookingException("Seat " + seat.getSeatNumber() + " is already booked");
            }
        });

        int ticketCount = seatIds.size();
        BigDecimal pricePerTicket = req.getPricePerTicket();
        BigDecimal basePrice = pricePerTicket.multiply(BigDecimal.valueOf(ticketCount));

        BigDecimal discount = BigDecimal.ZERO;
        StringBuilder discountNote = new StringBuilder();
        // 50% off on 3rd ticket
        if (ticketCount >= 3) {
            discount = discount.add(pricePerTicket.multiply(BigDecimal.valueOf(0.5)));
            discountNote.append("50% off 3rd ticket");
        }

        // 20% afternoon discount
        if (ShowService.isAfternoonShow(show.getShowTime())) {
            BigDecimal remaining = basePrice.subtract(discount);
            discount = discount.add(remaining.multiply(BigDecimal.valueOf(0.2)));

            if (discountNote.length() > 0) discountNote.append(" + ");
            discountNote.append("20% afternoon show discount");
        }

        // Final calculation
        BigDecimal totalDiscount = discount.setScale(2, RoundingMode.HALF_UP);
        BigDecimal finalPrice = basePrice.subtract(totalDiscount)
                .setScale(2, RoundingMode.HALF_UP);

        // Persist booking
        Booking booking = new Booking();
        booking.setShowId(req.getShowId());
        booking.setUserId(req.getUserId());
        booking.setBasePrice(basePrice);
        booking.setDiscountAmount(totalDiscount);
        booking.setFinalPrice(finalPrice);
        booking.setStatus("CONFIRMED");
        booking.setBookedAt(LocalDateTime.now());
        booking = bookingRepository.save(booking);

        // Mark seats as booked
        final Integer bookingId = booking.getId();
        List<String> seatNumbers = new ArrayList<>();
        for (Seat seat : seats) {
            seat.setIsBooked(true);
            seatRepository.save(seat);
            BookingSeat bs = new BookingSeat(null, bookingId, seat.getId());
            bookingSeatRepository.save(bs);
            seatNumbers.add(seat.getSeatNumber());
        }

        // Update show available seats
        show.setAvailableSeats(show.getAvailableSeats() - ticketCount);
        showRepository.save(show);

        return BookingResponse.builder()
                .bookingId(bookingId)
                .showId(req.getShowId())
                .userId(req.getUserId())
                .seatNumbers(seatNumbers)
                .basePrice(basePrice)
                .discountAmount(totalDiscount)
                .finalPrice(finalPrice)
                .discountNote(discountNote.length() > 0 ? discountNote.toString() : "No discount applied")
                .status("CONFIRMED")
                .build();
    }

    // Cancel a booking

    @Transactional
    public Map<String, String> cancelBooking(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingException("Booking not found: " + bookingId));
        if ("CANCELLED".equals(booking.getStatus())) {
            throw new BookingException("Booking already cancelled");
        }

        // Free seats
        List<BookingSeat> bookingSeats = bookingSeatRepository.findByBookingId(bookingId);
        bookingSeats.forEach(bs -> {
            seatRepository.findById(bs.getSeatId()).ifPresent(seat -> {
                seat.setIsBooked(false);
                seatRepository.save(seat);
            });
        });

        // Restore available seats on show
        showRepository.findById(booking.getShowId()).ifPresent(show -> {
            show.setAvailableSeats(show.getAvailableSeats() + bookingSeats.size());
            showRepository.save(show);
        });

        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);
        return Map.of("message", "Booking " + bookingId + " cancelled successfully");
    }

    // Get bookings for a user
    public List<Booking> getBookingsByUser(String userId) {
        return bookingRepository.findByUserId(userId);
    }
}
