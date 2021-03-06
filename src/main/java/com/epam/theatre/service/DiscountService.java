package com.epam.theatre.service;

import java.time.LocalDate;

import com.epam.theatre.domain.Event;
import com.epam.theatre.domain.Customer;

public interface DiscountService {

	Double getDiscount(Customer user, Event event, LocalDate airDate, Long ordinalNumberTicket);

}
