package com.booking.movie_platform.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "booking_seat")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BookingSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer bookingId;
    private Integer seatId;
}
