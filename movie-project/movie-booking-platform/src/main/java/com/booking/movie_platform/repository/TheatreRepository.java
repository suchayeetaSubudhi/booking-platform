package com.booking.movie_platform.repository;

import com.booking.movie_platform.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TheatreRepository extends JpaRepository<Theatre, Integer> {
    List<Theatre> findByCity(String city);
}
