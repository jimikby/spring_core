package com.epam.theatre.service;

import java.util.List;

import com.epam.theatre.domain.DomainObject;

public interface AbstractDomainObjectService<T extends DomainObject> {

	Long save(T object);

	void remove(Long object);

	T getById(Long id);

	List<T> getAll();

}