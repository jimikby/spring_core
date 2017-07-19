package com.epam.theatre.service;

import java.util.List;
import java.util.Set;

import com.epam.theatre.domain.Auditorium;

public interface AuditoriumService {

	List<Auditorium> getAll();

	Long getSeatsNumber(Auditorium auditorium);

	Set<Long> getVipSeats(Auditorium auditorium);

	Auditorium getByName(String auditoriumName);

	Auditorium getById(Long Auditorium);

}
