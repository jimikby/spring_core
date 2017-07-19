package com.epam.theatre.service;

import java.util.Set;
import com.epam.theatre.domain.Ticket;

public interface BookingService {

	Set<Long> bookTickets(Set<Ticket> tickets);

	Set<Ticket> takeTicketsWithPrices(Long eventScheduleId, Set<Long> seats, Long userId);

	Set<Long> checkSeats(Set<Long> seats, Long eventScheduleId);

}
