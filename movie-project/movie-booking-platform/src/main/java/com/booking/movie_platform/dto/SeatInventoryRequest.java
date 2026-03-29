package com.booking.movie_platform.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SeatInventoryRequest {
    private Integer showId;
    private Integer totalSeats;   // new total seat count
}
