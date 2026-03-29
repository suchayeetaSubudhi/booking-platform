package com.booking.movie_platform.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BookingResponse {
    private Integer bookingId;
    private Integer showId;
    private String userId;
    private List<String> seatNumbers;
    private BigDecimal basePrice;
    private BigDecimal discountAmount;
    private BigDecimal finalPrice;
    private String discountNote;
    private String status;
}
