package com.epam.theatre.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.epam.theatre.dao.CounterDao;
import com.epam.theatre.dao.impl.rowmapper.CounterRowMapper;
import com.epam.theatre.domain.Counter;

@Repository
public class CounterDaoImpl implements CounterDao {

	private enum CounterSqlQuery {

		SQL_TAKE_ALL("select COUNTER_ID, TYPE_NAME, TYPE_ID, COUNTER_NAME, COUNTER_VALUE from COUNTER"), //

		SQL_SAVE("insert into COUNTER (TYPE_NAME, TYPE_ID, COUNTER_NAME, COUNTER_VALUE) values "
				+ "(?,?,?,?)"), //

		SQL_REMOVE("delete from COUNTER where COUNTER_ID = ?"), //

		SQL_TAKE_BY_ID(
				"select COUNTER_ID, TYPE_NAME, TYPE_ID, COUNTER_NAME, COUNTER_VALUE from COUNTER where COUNTER_ID = ?"), //

		SQL_TAKE_BY_CLASS_NAME_AND_CLASS_ID(
				"select COUNTER_ID, TYPE_NAME, TYPE_ID, COUNTER_NAME, COUNTER_VALUE from COUNTER where  TYPE_NAME = ? AND COUNTER_NAME = ? AND TYPE_ID = ? "), //

		SQL_TAKE_SUM_COUNTER("select SUM(COUNTER_VALUE) from COUNTER where COUNTER_NAME = ?"), //

		SQL_UPDATE_COUNTER("update COUNTER set COUNTER_VALUE = ? where COUNTER_ID = ?");

		final String query;

		CounterSqlQuery(String query) {
			this.query = query;
		}

	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Long save(Counter counter) {

		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(CounterSqlQuery.SQL_SAVE.query,
						Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, counter.getTypeName());
				ps.setLong(2, counter.getTypeId());
				ps.setString(3, counter.getCounterName());
				ps.setLong(4, counter.getCounterValue());

				return ps;
			}
		}, holder);

		return holder.getKey().longValue();

	}

	@Override
	public void remove(Long counterId) {
		jdbcTemplate.update(CounterSqlQuery.SQL_REMOVE.query, counterId);
	}

	@Override
	public Counter getById(Long counterId) {

		try {
			return jdbcTemplate.queryForObject(CounterSqlQuery.SQL_TAKE_BY_ID.query, new Object[] { counterId },
					new CounterRowMapper());

		} catch (

		EmptyResultDataAccessException e) {
			return null;
		}

	}

	@Override
	public List<Counter> getAll() {
		return jdbcTemplate.query(CounterSqlQuery.SQL_TAKE_ALL.query, new CounterRowMapper());
	}

	@Override
	public Counter takeByClassNameAndClassId(String className, String methodName, Long classId) {

		Counter counter;
		try {
			counter = jdbcTemplate.queryForObject(CounterSqlQuery.SQL_TAKE_BY_CLASS_NAME_AND_CLASS_ID.query,
					new Object[] { className, methodName, classId }, new CounterRowMapper());
		} catch (EmptyResultDataAccessException e) {
			counter = null;
		}

		return counter;

	}

	@Override
	public void update(Long counterId, Long counterValue) {
		jdbcTemplate.update(CounterSqlQuery.SQL_UPDATE_COUNTER.query, counterValue, counterId);
	}

	@Override
	public Long takeAllCountersValueByClassName(String className) {
		return jdbcTemplate.queryForObject(CounterSqlQuery.SQL_TAKE_SUM_COUNTER.query, new Object[] { className },
				new RowMapper<Long>() {
					public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getLong(1);

					}
				});

	}

}
