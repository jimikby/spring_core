package com.epam.theatre.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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
import org.springframework.transaction.annotation.Transactional;

import com.epam.theatre.dao.CustomerDao;
import com.epam.theatre.dao.impl.rowmapper.CustomerRowMapper;
import com.epam.theatre.domain.Customer;

@Repository
@Transactional
public class CustomerDaoImpl implements CustomerDao {

	private enum CustomerSqlQuery {

		SQL_TAKE_ALL("select CUSTOMER_ID, FIRST_NAME, LAST_NAME, BIRTHDAY, EMAIL, PASSWORD, ROLE from CUSTOMER"), //

		SQL_SAVE("insert into CUSTOMER (FIRST_NAME, LAST_NAME, BIRTHDAY, EMAIL, PASSWORD, ROLE) values (?,?,?,?,?)"), //

		SQL_REMOVE("delete from CUSTOMER where CUSTOMER_ID = ?"), //

		SQL_TAKE_BY_ID(
				"select CUSTOMER_ID, FIRST_NAME, LAST_NAME, EMAIL, BIRTHDAY, PASSWORD, ROLE from CUSTOMER where CUSTOMER_ID = ?"), //

		SQL_TAKE_BY_EMAIL(
				"select CUSTOMER_ID, FIRST_NAME, LAST_NAME, EMAIL, BIRTHDAY, PASSWORD, ROLE from CUSTOMER where EMAIL = ?"), //
		SQL_TAKE_BY_EMAIL_AND_PASSWORD(
				"select CUSTOMER_ID, FIRST_NAME, LAST_NAME, EMAIL, BIRTHDAY, PASSWORD, ROLE from CUSTOMER where EMAIL = ? and PASSWORD = ?"), //
		SQL_UPDATE_BY_ID(
				"update CUSTOMER set FIRST_NAME = ?, LAST_NAME = ?, PASSWORD = ?, BIRTHDAY = ?, ROLE = ? WHERE CUSTOMER_ID = ?");

		final String query;

		CustomerSqlQuery(String query) {
			this.query = query;
		}

	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Long save(Customer customer) {

		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

				PreparedStatement ps = connection.prepareStatement(CustomerSqlQuery.SQL_SAVE.query,
						Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, customer.getFirstName());
				ps.setString(2, customer.getLastName());
				ps.setTimestamp(3, Timestamp.valueOf(customer.getBirthDay().atStartOfDay()));
				ps.setString(4, customer.getEmail());
				ps.setString(5, customer.getPassword());
				ps.setString(6, String.join(",", customer.getRole()));
				return ps;
			}
		}, holder);

		return holder.getKey().longValue();

	}

	@Override
	public void remove(Long cutomerId) {

		jdbcTemplate.update(CustomerSqlQuery.SQL_REMOVE.query, cutomerId);
	}

	@Override
	public Customer getById(Long cutomerId) {

		try {
			return jdbcTemplate.queryForObject(CustomerSqlQuery.SQL_TAKE_BY_ID.query, new Object[] { cutomerId },
					new CustomerRowMapper());

		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Customer> getAll() {
		return jdbcTemplate.query(CustomerSqlQuery.SQL_TAKE_ALL.query, new CustomerRowMapper());
	}

	@Override
	public Customer getUserByEmail(String cutomerEmail) {

		try {
			return jdbcTemplate.queryForObject(CustomerSqlQuery.SQL_TAKE_BY_EMAIL.query, new Object[] { cutomerEmail },
					new CustomerRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	@Override
	public Customer takeByEmailPassword(String email, String password) {

		Customer customer;
		try {
			customer = jdbcTemplate.queryForObject(CustomerSqlQuery.SQL_TAKE_BY_EMAIL_AND_PASSWORD.query,
					new Object[] { email, password }, new CustomerRowMapper());
		} catch (EmptyResultDataAccessException e) {
			customer = null;
		}

		return customer;

	}

	@Override
	public void updateById(Long customerId, Customer customer) {

		jdbcTemplate.update(CustomerSqlQuery.SQL_UPDATE_BY_ID.query, new Object[] { customer.getFirstName(),
				customer.getLastName(), customer.getPassword(), customer.getBirthDay(),

		});

	}

	@Override
	public void saveAll(List<Customer> customers) {
		List<Customer> list = new ArrayList<>(customers);
		jdbcTemplate.batchUpdate(CustomerSqlQuery.SQL_SAVE.query, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Customer customer = list.get(i);
				ps.setString(1, customer.getFirstName());
				ps.setString(2, customer.getLastName());
				ps.setTimestamp(3, Timestamp.valueOf(customer.getBirthDay().atStartOfDay()));
				ps.setString(4, customer.getEmail());
				ps.setString(5, customer.getPassword());
			}

			@Override
			public int getBatchSize() {
				return customers.size();
			}
		});
	}

}
