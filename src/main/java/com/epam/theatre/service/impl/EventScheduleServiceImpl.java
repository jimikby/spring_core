package com.epam.theatre.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.theatre.dao.EventScheduleDao;
import com.epam.theatre.domain.EventSchedule;
import com.epam.theatre.service.EventScheduleService;

@Service
public class EventScheduleServiceImpl implements EventScheduleService {

	@Autowired
	private EventScheduleDao eventScheduleDao;

	@Override
	public Long save(EventSchedule eventScheule) {
		return eventScheduleDao.save(eventScheule);
	}

	@Override
	public void remove(Long eventScheuleId) {
		eventScheduleDao.remove(eventScheuleId);
	}

	@Override
	public EventSchedule getById(Long eventScheuleId) {
		return eventScheduleDao.getById(eventScheuleId);

	}

	@Override
	public List<EventSchedule> getAll() {
		return eventScheduleDao.getAll();
	}

	@Override
	public  List<EventSchedule> takeByEventId(Long eventId) {
		return eventScheduleDao.takeByEventId(eventId);
	}

}
