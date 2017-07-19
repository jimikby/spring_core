package com.epam.theatre.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.epam.theatre.dao.SqlFields;
import com.epam.theatre.domain.Ticket;

public class TicketRowMapper implements RowMapper<Ticket> {

	@Override
	public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {

		Ticket ticket = new Ticket();
		ticket.setTicketId(rs.getLong(SqlFields.TICKET_ID.name()));
		ticket.setTicketCost(rs.getDouble(SqlFields.TICKET_COST.name()));
		ticket.setSeat(rs.getLong(SqlFields.SEAT.name()));
		ticket.setEventScheduleId(rs.getLong(SqlFields.EVENT_SCHEDULE_ID.name()));
		ticket.setUserId(rs.getLong(SqlFields.CUSTOMER_ID.name()));
		ticket.setDiscount(rs.getDouble(SqlFields.DISCOUNT.name()));

		return ticket;
	}

}