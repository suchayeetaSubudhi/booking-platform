package com.booking.movie_platform.dto;

import java.util.List;
import com.booking.movie_platform.entity.Show;
import com.booking.movie_platform.entity.Theatre;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TheatreShowsResponse {
    private Theatre theatre;
    private List<Show> shows;
}
