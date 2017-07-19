package com.epam.theatre.logic.impl;

import java.time.LocalDate;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.epam.theatre.domain.Event;
import com.epam.theatre.domain.Customer;
import com.epam.theatre.logic.DiscountStrategy;

@Component("birthdayStrategy")
public class BirthdayStrategyImpl implements DiscountStrategy {

	@Value("${days.before.bithday.discount}")
	private Integer daysBeforeBirthdayDiscount;

	@Value("${birthday.discount}")
	private Double birthDayDiscount;

	private static final Logger LOGGER = LogManager.getLogger(DiscountStrategy.class);

	public Double getDiscount(Customer user, Event event, LocalDate airDate, Long numbersOfTickets) {

		Double discount = null;

		if (user != null) {

			if (user.getBirthDay().plusDays(daysBeforeBirthdayDiscount + 1).isAfter(airDate)) {

				discount = birthDayDiscount;

				LOGGER.log(Level.INFO, "User birthday: " + user.getBirthDay() + " |5 ... 0 days| Air date: " + airDate);

			}

		}

		return discount;

	}

}
