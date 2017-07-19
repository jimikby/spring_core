package com.epam.theatre.service;

import com.epam.theatre.domain.Counter;
import com.epam.theatre.service.AbstractDomainObjectService;

public interface CounterService extends AbstractDomainObjectService<Counter> {

	Counter takeByClassNameAndClassId(String className, String methodName, Long classId);

	void update(Long counterId, Long counterValue);

	Long takeAllCountersValueByClassName(String className);

}
