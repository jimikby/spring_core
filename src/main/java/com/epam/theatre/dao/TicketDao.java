package com.epam.theatre.dao;

import java.util.List;
import java.util.Set;

import com.epam.theatre.domain.Ticket;

public interface TicketDao extends AbstractDomainObjectDao<Ticket> {

	List<Ticket> getTicketsByUserId(Long userId);

	List<Ticket> takeTicketsByEventScheduleId(Long eventScheduleId);

	boolean checkSeat(Long seat, Long eventScheduleId);

	Long getTicketsQuantityByUserId(Long userId);

	void saveAll(Set<Ticket> tickets);

}
