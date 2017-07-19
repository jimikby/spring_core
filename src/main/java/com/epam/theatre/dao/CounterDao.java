package com.epam.theatre.dao;

import com.epam.theatre.domain.Counter;

public interface CounterDao extends AbstractDomainObjectDao<Counter> {

	Counter takeByClassNameAndClassId(String className, String methodName, Long classId);

	void update(Long classId, Long counterValue);

	Long takeAllCountersValueByClassName(String className);

}
