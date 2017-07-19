package com.epam.theatre.service;

import java.util.Set;
import com.epam.theatre.domain.Ticket;

public interface BookingService {

	void bookTickets(Set<Ticket> tickets, Long customerId);

	Set<Ticket> takeTicketsWithPrices(Long eventScheduleId, Set<Long> seats, Long userId);

	Set<Long> checkSeats(Set<Long> seats, Long eventScheduleId);

}
