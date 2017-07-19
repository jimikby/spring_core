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
	private CounterDao counterDao;

	@Override
	public Long save(Counter counter) {
		return counterDao.save(counter);
	}

	@Override
	public void remove(Long counterId) {
		counterDao.remove(counterId);

	}

	@Override
	public Counter getById(Long counterId) {

		return counterDao.getById(counterId);
	}

	@Override
	public List<Counter> getAll() {

		return counterDao.getAll();
	}

	@Override
	public Counter takeByClassNameAndClassId(String className, String methodName, Long classId) {
		return counterDao.takeByClassNameAndClassId(className, methodName, classId);
	}

	@Override
	public void update(Long counterId, Long counterValue) {
		counterDao.update(counterId, counterValue);
	}

	@Override
	public Long takeAllCountersValueByClassName(String className) {
		return counterDao.takeAllCountersValueByClassName(className);
	}

}
