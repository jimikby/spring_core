package com.epam.theatre.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.theatre.dao.CounterDao;
import com.epam.theatre.domain.Counter;
import com.epam.theatre.service.CounterService;

@Service
public class CounterServiceImpl implements CounterService {

	@Autowired
	private CounterDao сounterDao;

	@Override
	public Long save(Counter сounter) {
		return сounterDao.save(сounter);
	}

	@Override
	public void remove(Long сounterId) {
		сounterDao.remove(сounterId);

	}

	@Override
	public Counter getById(Long сounterId) {

		return сounterDao.getById(сounterId);
	}

	@Override
	public List<Counter> getAll() {

		return сounterDao.getAll();
	}

	@Override
	public Counter takeByClassNameAndClassId(String className, String methodName, Long classId) {
		return сounterDao.takeByClassNameAndClassId(className, methodName, classId);
	}

	@Override
	public void update(Long counterId, Long counterValue) {
		сounterDao.update(counterId, counterValue);
	}

	@Override
	public Long takeAllCountersValueByClassName(String className) {
		return сounterDao.takeAllCountersValueByClassName(className);
	}

}
