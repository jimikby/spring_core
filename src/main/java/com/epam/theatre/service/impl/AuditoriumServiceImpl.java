package com.epam.theatre.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.theatre.domain.Auditorium;
import com.epam.theatre.service.AuditoriumService;

@Service
public class AuditoriumServiceImpl implements AuditoriumService {

	@Autowired
	private List<Auditorium> auditoriumList;

	public List<Auditorium> getAll() {

		return auditoriumList;
	}

	@Override
	public Long getSeatsNumber(Auditorium auditorium) {
		return auditorium.getNumberOfSeats();
	}

	@Override
	public Set<Long> getVipSeats(Auditorium auditorium) {
		return auditorium.getVipSeats();
	}

	@Override
	public Auditorium getByName(String name) {
		Optional<Auditorium> auditorium = getAll().stream().filter(a -> (a.getName().equals(name))).findFirst();

		if (!auditorium.isPresent()) {

			return null;
		}

		return auditorium.get();
	}

	@Override
	public Auditorium getById(Long id) {

		Auditorium auditorium = null;

		for (Auditorium auditorium1 : auditoriumList) {

			if (auditorium1.getAuditoriumId() == id) {
				auditorium = auditorium1;

			}
		}
		return auditorium;
	}

}
