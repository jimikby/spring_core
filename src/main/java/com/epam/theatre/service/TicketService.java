package com.epam.theatre.service;

import java.util.List;
import java.util.Set;

import com.epam.theatre.domain.Ticket;
import com.epam.theatre.service.AbstractDomainObjectService;

public interface TicketService extends AbstractDomainObjectService<Ticket> {

	Long save(Ticket ticket);

	List<Ticket> getTicketsByUserId(Long userId);

	List<Ticket> takeTicketsByEventScheduleId(Long eventScheduleId);

	boolean checkSeat(Long seat, Long eventScheduleId);

	Long getTicketsQuantityByUserId(Long userId);

	void saveAll(Set<Ticket> tickets);

	List<Ticket> takeTicketsByEventId(Long eventId);

}
