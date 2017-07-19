package com.epam.theatre.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.epam.theatre.dao.SqlFields;
import com.epam.theatre.domain.Counter;

public class CounterRowMapper implements RowMapper<Counter> {

	@Override
	public Counter mapRow(ResultSet rs, int rowNum) throws SQLException {

		Counter сounter = new Counter();
		сounter.setCounterId(rs.getLong(SqlFields.COUNTER_ID.name()));
		сounter.setTypeName(rs.getString(SqlFields.TYPE_NAME.name()));
		сounter.setTypeId(rs.getLong(SqlFields.TYPE_ID.name()));
		сounter.setCounterName(rs.getString(SqlFields.COUNTER_NAME.name()));
		сounter.setCounterValue(rs.getLong(SqlFields.COUNTER_VALUE.name()));

		return сounter;
	}

}
