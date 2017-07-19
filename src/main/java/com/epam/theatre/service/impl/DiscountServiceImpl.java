package com.epam.theatre.service.impl;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.epam.theatre.domain.Event;
import com.epam.theatre.domain.Customer;
import com.epam.theatre.logic.DiscountStrategy;
import com.epam.theatre.service.DiscountService;

@Service
public class DiscountServiceImpl implements DiscountService {

	@Resource(name = "discountStrategies")
	private List<DiscountStrategy> discountStrategies;

	@Override
	public Double getDiscount(Customer user, Event event, LocalDate airDate, Long ordinalNumberTicket) {

		Double value = 0.0;

		for (DiscountStrategy discountStrategy : discountStrategies) {

			Double newValue = discountStrategy.getDiscount(user, event, airDate, ordinalNumberTicket);

			if (newValue > value) {
				value = newValue;
			}
		}

		return value;

	}
}