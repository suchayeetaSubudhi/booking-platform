package com.booking.movie_platform.dto;

import java.time.LocalDate;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ShowRequest {
    private Integer movieId;
    private Integer theatreId;
    private String showTime;
    private LocalDate showDate;
    private Integer totalSeats;
}
