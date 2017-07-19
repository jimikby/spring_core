package com.epam.theatre.dao;

import java.util.List;

import com.epam.theatre.domain.Event;

public interface EventDao extends AbstractDomainObjectDao<Event> {

	Event getByName(String name);

	void saveAll(List<Event> events);

	void update(Long eventId, Event event);

}
