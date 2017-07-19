package com.epam.theatre.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.epam.theatre.dao.SqlFields;
import com.epam.theatre.domain.EventSchedule;

public class EventScheduleRowMapper implements RowMapper<EventSchedule> {

	@Override
	public EventSchedule mapRow(ResultSet rs, int rowNum) throws SQLException {

		EventSchedule eventSchedule = new EventSchedule();
		eventSchedule.setEventScheudleId(rs.getLong(SqlFields.EVENT_SCHEDULE_ID.name()));
		eventSchedule.setEventDate(rs.getTimestamp(SqlFields.EVENT_DATE.name()).toLocalDateTime());
		eventSchedule.setEventId(rs.getLong(SqlFields.EVENT_ID.name()));
		eventSchedule.setAuditoriumId(rs.getLong(SqlFields.AUDITORIUM_ID.name()));

		return eventSchedule;
	}

}