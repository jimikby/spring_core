package com.epam.theatre.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.epam.theatre.dao.EventScheduleDao;
import com.epam.theatre.dao.impl.rowmapper.EventScheduleRowMapper;
import com.epam.theatre.domain.EventSchedule;

@Repository
public class EventScheduleDaoImpl implements EventScheduleDao {

	private enum EventScheduleSqlQuery {

		SQL_TAKE_ALL("select EVENT_SCHEDULE_ID, EVENT_DATE, EVENT_ID, AUDITORIUM_ID from EVENT_SCHEDULE"), //

		SQL_SAVE(
				"insert into EVENT_SCHEDULE (EVENT_DATE, EVENT_ID, AUDITORIUM_ID) values (?,?,?)"), //

		SQL_REMOVE("delete from EVENT_SCHEDULE where EVENT_SCHEDULE_ID = ?"), //

		SQL_TAKE_BY_ID(
				"select EVENT_SCHEDULE_ID, EVENT_DATE, EVENT_ID, AUDITORIUM_ID from EVENT_SCHEDULE where EVENT_SCHEDULE_ID = ?"), //
		SQL_TAKE_BY_EVENT_ID(
				"select EVENT_SCHEDULE_ID, EVENT_DATE, EVENT_ID, AUDITORIUM_ID from EVENT_SCHEDULE where EVENT_ID = ?");//

		final String query;

		EventScheduleSqlQuery(String query) {
			this.query = query;
		}

	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Long save(EventSchedule eventSchedule) {

		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(EventScheduleSqlQuery.SQL_SAVE.query,
						Statement.RETURN_GENERATED_KEYS);
				ps.setTimestamp(1, Timestamp.valueOf(eventSchedule.getEventDate()));
				ps.setLong(2, eventSchedule.getEventId());
				ps.setLong(3, eventSchedule.getAuditoriumId());

				return ps;
			}
		}, holder);

		return holder.getKey().longValue();

	}

	@Override
	public void remove(Long eventScheduleId) {
		jdbcTemplate.update(EventScheduleSqlQuery.SQL_REMOVE.query, eventScheduleId);
	}

	@Override
	public EventSchedule getById(Long eventScheduleId) {

		try {
			return jdbcTemplate.queryForObject(EventScheduleSqlQuery.SQL_TAKE_BY_ID.query,
					new Object[] { eventScheduleId }, new EventScheduleRowMapper());

		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<EventSchedule> getAll() {
		return jdbcTemplate.query(EventScheduleSqlQuery.SQL_TAKE_ALL.query, new EventScheduleRowMapper());
	}

	@Override
	public List<EventSchedule> takeByEventId(Long eventId) {
		return jdbcTemplate.query(EventScheduleSqlQuery.SQL_TAKE_BY_EVENT_ID.query,new Object[] { eventId }, new EventScheduleRowMapper());
	}

}
