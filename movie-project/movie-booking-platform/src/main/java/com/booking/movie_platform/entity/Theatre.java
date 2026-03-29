package com.booking.movie_platform.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Theatre {
	@Id
	private Integer id;
	private String name;
	private String city;
}