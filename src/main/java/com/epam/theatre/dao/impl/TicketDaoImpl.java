package com.epam.theatre.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.epam.theatre.dao.TicketDao;
import com.epam.theatre.dao.impl.rowmapper.TicketRowMapper;
import com.epam.theatre.domain.Ticket;

@Repository
@Transactional
public class TicketDaoImpl implements TicketDao {

	private enum TicketSqlQuery {

		SQL_TAKE_ALL("select TICKET_ID, CUSTOMER_ID, TICKET_COST, SEAT, EVENT_SCHEDULE_ID, DISCOUNT from TICKET"), //

		SQL_SAVE("insert into TICKET (CUSTOMER_ID, TICKET_COST, SEAT, EVENT_SCHEDULE_ID, DISCOUNT) "
				+ "values (?,?,?,?,?)"), //

		SQL_REMOVE("delete from TICKET where TICKET_ID = ?"), //

		SQL_TAKE_BY_ID("select TICKET_ID, CUSTOMER_ID, TICKET_COST, SEAT, EVENT_SCHEDULE_ID, DISCOUNT from "
				+ "TICKET where TICKET_ID = ?"), //

		SQL_CHECK_SEAT("select TICKET_ID, CUSTOMER_ID, TICKET_COST, SEAT, EVENT_SCHEDULE_ID, DISCOUNT from "
				+ "TICKET where SEAT = ? and EVENT_SCHEDULE_ID = ?"), //

		SQL_TAKE_TICKETS_BY_CUSTOMER_ID(
				"select TICKET_ID, CUSTOMER_ID, TICKET_COST, SEAT, EVENT_SCHEDULE_ID, DISCOUNT from "
						+ "TICKET where TICKET_ID = ?"), //

		SQL_TAKE_TICKET_BY_EVENT_SCHEDULE_ID(
				"select TICKET_ID, CUSTOMER_ID, TICKET_COST, SEAT, EVENT_SCHEDULE_ID, DISCOUNT from TICKET where EVENT_SCHEDULE_ID = ?"), //

		SQL_TAKE_TICKET_QUANTITY_BY_CUSTOMER_ID("select count(TICKET_ID) from TICKET where CUSTOMER_ID = ?"); //

		final String query;

		TicketSqlQuery(String query) {
			this.query = query;
		}

	}

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Long save(Ticket ticket) {

		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(TicketSqlQuery.SQL_SAVE.query,
						Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, ticket.getUserId());
				ps.setDouble(2, ticket.getTicketCost());
				ps.setLong(3, ticket.getSeat());
				ps.setLong(4, ticket.getEventScheduleId());
				ps.setDouble(5, ticket.getDiscount());

				return ps;
			}
		}, holder);

		return holder.getKey().longValue();

	}

	@Override
	public void remove(Long ticketId) {

		jdbcTemplate.update(TicketSqlQuery.SQL_REMOVE.query, ticketId);

	}

	@Override
	public Ticket getById(Long ticketId) {

		try {
			return jdbcTemplate.queryForObject(TicketSqlQuery.SQL_TAKE_BY_ID.query, new Object[] { ticketId },
					new TicketRowMapper());

		} catch (

		EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Ticket> getAll() {
		return jdbcTemplate.query(TicketSqlQuery.SQL_TAKE_ALL.query, new TicketRowMapper());
	}

	@Override
	public List<Ticket> getTicketsByUserId(Long userId) {
		try {
			return jdbcTemplate.query(TicketSqlQuery.SQL_TAKE_TICKETS_BY_CUSTOMER_ID.query, new Object[] { userId },
					new TicketRowMapper());

		} catch (

		EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Ticket> takeTicketsByEventScheduleId(Long eventScheduleId) {

		try {
			return jdbcTemplate.query(TicketSqlQuery.SQL_TAKE_TICKET_BY_EVENT_SCHEDULE_ID.query,
					new Object[] { eventScheduleId }, new TicketRowMapper());

		} catch (

		EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public boolean checkSeat(Long seat, Long eventScheduleId) {

		boolean value;
		try {

			value = jdbcTemplate.queryForObject(TicketSqlQuery.SQL_CHECK_SEAT.query,
					new Object[] { seat, eventScheduleId }, new TicketRowMapper()) != null;
		}

		catch (EmptyResultDataAccessException e) {
			value = false;
		}

		return value;

	}

	@Override
	public Long getTicketsQuantityByUserId(Long userId) {
		return jdbcTemplate.queryForObject(TicketSqlQuery.SQL_TAKE_TICKET_QUANTITY_BY_CUSTOMER_ID.query,
				new Object[] { userId }, new RowMapper<Long>() {

					public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getLong(1);

					}
				});
	}

	@Override
	public void saveAll(Set<Ticket> tickets) {
		List<Ticket> list = new ArrayList<>(tickets);
		jdbcTemplate.batchUpdate(TicketSqlQuery.SQL_SAVE.query, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Ticket ticket = list.get(i);
				ps.setLong(1, ticket.getUserId());
				ps.setDouble(2, ticket.getTicketCost());
				ps.setLong(3, ticket.getSeat());
				ps.setLong(4, ticket.getEventScheduleId());
				ps.setDouble(5, ticket.getDiscount());
			}

			@Override
			public int getBatchSize() {
				return tickets.size();
			}
		});
	}
}
