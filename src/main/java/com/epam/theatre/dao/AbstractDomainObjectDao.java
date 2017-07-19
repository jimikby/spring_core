package com.epam.theatre.dao;

import java.util.List;

import com.epam.theatre.domain.DomainObject;

public interface AbstractDomainObjectDao<T extends DomainObject> {

	Long save(T object);

	void remove(Long objectId);

	T getById(Long id);

	List<T> getAll();

}
