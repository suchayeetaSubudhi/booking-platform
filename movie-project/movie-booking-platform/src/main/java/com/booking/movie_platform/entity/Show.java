package com.booking.movie_platform.entity;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "show_details")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer movieId;
    private Integer theatreId;
    private String showTime;   // "HH:mm" – afternoon = 12:00–16:59
    private LocalDate showDate;
    private Integer totalSeats;
    private Integer availableSeats;
}
