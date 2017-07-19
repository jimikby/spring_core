package com.epam.theatre.logic.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.epam.theatre.domain.Event;
import com.epam.theatre.domain.Customer;
import com.epam.theatre.logic.DiscountStrategy;

@Component("everyTenStrategy")
public class EveryTenStrategyImpl implements DiscountStrategy {

	@Value("${days.before.bithday.discount}")
	private Integer daysBeforeBirthdayDiscount;

	@Value("${every.ten.discount}")
	private Double everyTenDiscount;

	public Double getDiscount(Customer user, Event event, LocalDate airDate, Long numbersOfTickets) {

		return numbersOfTickets % 10 == 0 ? everyTenDiscount : 0;

	}

}
