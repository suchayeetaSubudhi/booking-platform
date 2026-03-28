package com.booking.movie_platform.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "show_details")
@Getter
@Setter
public class Show {

	@Id
	private Integer id;

	private Integer movieId;
	private Integer theatreId;
	private String showTime;
	private LocalDate showDate;
}