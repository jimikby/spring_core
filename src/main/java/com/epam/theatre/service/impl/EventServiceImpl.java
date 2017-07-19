package com.epam.theatre.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.theatre.dao.EventDao;
import com.epam.theatre.domain.Event;
import com.epam.theatre.service.EventService;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	private EventDao eventDao;

	@Override
	public Long save(Event event) {
		return eventDao.save(event);
	}

	@Override
	public void remove(Long eventId) {
		eventDao.remove(eventId);
	}

	@Override
	public Event getById(Long eventId) {
		return eventDao.getById(eventId);

	}

	@Override
	public List<Event> getAll() {
		return eventDao.getAll();
	}

	@Override
	public Event getByName(String eventName) {
		return eventDao.getByName(eventName);
	}

	@Override
	public void saveAll(List<Event> events) {
		eventDao.saveAll(events);

	}

	@Override
	public void update(Long eventId, Event event) {
		eventDao.update(eventId, event);

	}

}
