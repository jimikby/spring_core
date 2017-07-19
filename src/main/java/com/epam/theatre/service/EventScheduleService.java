package com.epam.theatre.service;

import java.util.List;

import com.epam.theatre.domain.EventSchedule;

public interface EventScheduleService extends AbstractDomainObjectService<EventSchedule> {

	 List<EventSchedule> takeByEventId(Long eventId);

}
