package com.epam.theatre.service;

import java.util.List;

import com.epam.theatre.domain.Event;

public interface EventService extends AbstractDomainObjectService<Event> {

	Event getByName(String name);

	void saveAll(List<Event> events);

	void update(Long eventId, Event event);

}
