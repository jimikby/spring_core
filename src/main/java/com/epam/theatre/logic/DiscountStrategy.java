package com.epam.theatre.logic;

import java.time.LocalDate;

import com.epam.theatre.domain.Event;
import com.epam.theatre.domain.Customer;

public interface DiscountStrategy {

	Double getDiscount(Customer user, Event event, LocalDate airDate, Long ordinalNumberTicket);

}