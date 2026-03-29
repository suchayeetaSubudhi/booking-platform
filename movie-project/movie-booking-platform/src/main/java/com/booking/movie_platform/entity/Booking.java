package com.booking.movie_platform.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer showId;
    private String userId;
    private BigDecimal basePrice;
    private BigDecimal discountAmount;
    private BigDecimal finalPrice;
    private String status;  // CONFIRMED / CANCELLED
    private LocalDateTime bookedAt;
}
