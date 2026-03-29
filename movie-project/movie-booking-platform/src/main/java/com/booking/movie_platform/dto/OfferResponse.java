package com.booking.movie_platform.dto;

import java.util.List;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OfferResponse {
    private String city;
    private String theatreName;
    private List<String> activeOffers;
}
