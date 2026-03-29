package com.booking.movie_platform.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BookingRequest {
    private Integer showId;
    private String userId;
    private List<Integer> seatIds;   // seat IDs to book
    private BigDecimal pricePerTicket;
}
