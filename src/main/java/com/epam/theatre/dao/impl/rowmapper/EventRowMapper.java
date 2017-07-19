package com.epam.theatre.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.epam.theatre.dao.SqlFields;
import com.epam.theatre.domain.Event;
import com.epam.theatre.domain.EventRating;

public class EventRowMapper implements RowMapper<Event> {

	@Override
	public Event mapRow(ResultSet rs, int rowNum) throws SQLException {

		Event event = new Event();

		event.setEventId(rs.getLong(SqlFields.EVENT_ID.name()));
		event.setBasePrice(rs.getLong(SqlFields.BASE_PRICE.name()));
		event.setEventName(rs.getString(SqlFields.EVENT_NAME.name()));
		event.setRating(EventRating.valueOf(rs.getString(SqlFields.EVENT_RATING.name())));

		return event;
	}

}
