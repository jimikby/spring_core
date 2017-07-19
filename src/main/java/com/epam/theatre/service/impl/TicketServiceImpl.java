package com.epam.theatre.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.theatre.dao.TicketDao;
import com.epam.theatre.domain.Ticket;
import com.epam.theatre.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketDao ticketDao;

	@Override
	public Long save(Ticket ticket) {
		return ticketDao.save(ticket);
	}

	@Override
	public void remove(Long ticketId) {
		ticketDao.remove(ticketId);

	}

	@Override
	public Ticket getById(Long id) {
		return ticketDao.getById(id);
	}

	@Override
	public List<Ticket> getAll() {
		return ticketDao.getAll();
	}

	@Override
	public List<Ticket> getTicketsByUserId(Long userId) {
		return ticketDao.getTicketsByUserId(userId);
	}

	@Override
	public List<Ticket> takeTicketsByEventScheduleId(Long eventScheduleId) {

		return ticketDao.takeTicketsByEventScheduleId(eventScheduleId);
	}

	@Override
	public boolean checkSeat(Long seat, Long eventScheduleId) {
		return ticketDao.checkSeat(seat, eventScheduleId);
	}

	@Override
	public Long getTicketsQuantityByUserId(Long userId) {
		return ticketDao.getTicketsQuantityByUserId(userId);
	}

	@Override
	public void saveAll(Set<Ticket> tickets) {
		ticketDao.saveAll(tickets);

	}

}
