package com.epam.theatre.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.epam.theatre.dao.EventDao;
import com.epam.theatre.dao.impl.rowmapper.EventRowMapper;
import com.epam.theatre.domain.Event;

@Repository
public class EventDaoImpl implements EventDao {

	private enum EventSqlQuery {

		SQL_TAKE_ALL("select EVENT_ID,BASE_PRICE,EVENT_NAME,EVENT_RATING from EVENT"), //

		SQL_SAVE("insert into EVENT (BASE_PRICE,EVENT_NAME,EVENT_RATING) values (?,?,?)"), //

		SQL_REMOVE("delete from EVENT where EVENT_ID = ?"), //

		SQL_TAKE_BY_ID("select EVENT_ID,BASE_PRICE,EVENT_NAME,EVENT_RATING from EVENT where EVENT_ID = ?"), //

		SQL_TAKE_BY_NAME("select EVENT_ID,BASE_PRICE,EVENT_NAME,EVENT_RATING from EVENT where EVENT_NAME = ?"),//
		
		SQL_UPDATE_BY_ID(
				"update EVENT set  EVENT_NAME = ?,  BASE_PRICE = ?,  EVENT_RATING = ?,  WHERE EVENT_ID = ?"); //

		final String query;

		EventSqlQuery(String query) {
			this.query = query;
		}

	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Long save(Event event) {

		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(EventSqlQuery.SQL_SAVE.query,
						Statement.RETURN_GENERATED_KEYS);
				ps.setDouble(1, event.getBasePrice());
				ps.setString(2, event.getEventName());
				ps.setString(3, event.getRating().name());

				return ps;
			}
		}, holder);

		return holder.getKey().longValue();

	}

	@Override
	public void remove(Long eventId) {
		jdbcTemplate.update(EventSqlQuery.SQL_REMOVE.query, eventId);
	}

	@Override
	public Event getById(Long eventId) {

		try {
			return jdbcTemplate.queryForObject(EventSqlQuery.SQL_TAKE_BY_ID.query, new Object[] { eventId },
					new EventRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Event> getAll() {
		return jdbcTemplate.query(EventSqlQuery.SQL_TAKE_ALL.query, new EventRowMapper());
	}

	@Override
	public Event getByName(String name) {
		try {
			return jdbcTemplate.queryForObject(EventSqlQuery.SQL_TAKE_BY_NAME.query, new Object[] { name },
					new EventRowMapper());

		} catch (EmptyResultDataAccessException e) {
			return null;

		}
	}

	@Override
	public void saveAll(List<Event> events) {
		List<Event> list = new ArrayList<>(events);
		jdbcTemplate.batchUpdate(EventSqlQuery.SQL_SAVE.query, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Event event = list.get(i);
				ps.setDouble(1, event.getBasePrice());
				ps.setString(2, event.getEventName());
				ps.setString(3, event.getRating().name());

			}

			@Override
			public int getBatchSize() {
				return events.size();
			}
		});
	}

	@Override
	public void update(Long eventId, Event event) {
			
			jdbcTemplate.update(EventSqlQuery.SQL_UPDATE_BY_ID.query,
					new Object[] { 
							event.getEventName(),
							event.getBasePrice(),
							event.getRating(),
							eventId,
					});
			

		}

}
