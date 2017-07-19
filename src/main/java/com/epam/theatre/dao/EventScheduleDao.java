package com.epam.theatre.dao;

import java.util.List;

import com.epam.theatre.domain.EventSchedule;

public interface EventScheduleDao extends AbstractDomainObjectDao<EventSchedule> {

	 List<EventSchedule> takeByEventId(Long eventId);

}
