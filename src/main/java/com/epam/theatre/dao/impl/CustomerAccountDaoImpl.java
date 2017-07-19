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
import org.springframework.transaction.annotation.Transactional;

import com.epam.theatre.dao.CustomerAccountDao;
import com.epam.theatre.dao.impl.rowmapper.CustomerAccountRowMapper;
import com.epam.theatre.domain.CustomerAccount;

@Repository
@Transactional
public class CustomerAccountDaoImpl implements CustomerAccountDao {

	private enum CustomerAccountSqlQuery {

		SQL_TAKE_ALL(
				"select CUSTOMER_ACCOUNT_ID, CUSTOMER_ID, CUSTOMER_MONEY from CUSTOMER_ACCOUNT"), //

		SQL_SAVE(
				"insert into CUSTOMER_ACCOUNT (CUSTOMER_ID, CUSTOMER_MONEY) values (?,?)"), //

		SQL_REMOVE("delete from CUSTOMER_ACCOUNT where CUSTOMER_ACCOUNT_ID = ?"), //

		SQL_TAKE_BY_ID(
				"select CUSTOMER_ACCOUNT_ID, CUSTOMER_ID, CUSTOMER_MONEY from CUSTOMER_ACCOUNT where CUSTOMER_ACCOUNT_ID = ?"), //

		SQL_UPDATE_BY_ID(
				"update CUSTOMER_ACCOUNT set CUSTOMER_MONEY = ? WHERE CUSTOMER_ACCOUNT_ID = ?");

		final String query;

		CustomerAccountSqlQuery(String query) {
			this.query = query;
		}

	}

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Long save(CustomerAccount customerAccount) {

		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

				PreparedStatement ps = connection.prepareStatement(CustomerAccountSqlQuery.SQL_SAVE.query,
						Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, customerAccount.getCustomerAccountId());
				ps.setDouble(2, customerAccount.getCustomerMoney());
				return ps;
			}
		}, holder);

		return holder.getKey().longValue();

	}

	@Override
	public void remove(Long customerAccountId) {
		jdbcTemplate.update(CustomerAccountSqlQuery.SQL_REMOVE.query, customerAccountId);
	}

	@Override
	public CustomerAccount getById(Long customerAccountId) {

		try {
			return jdbcTemplate.queryForObject(CustomerAccountSqlQuery.SQL_TAKE_BY_ID.query, new Object[] { customerAccountId },
					new CustomerAccountRowMapper());

		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<CustomerAccount> getAll() {
		return jdbcTemplate.query(CustomerAccountSqlQuery.SQL_TAKE_ALL.query, new CustomerAccountRowMapper());
	}

	@Override
	public void updateById(Long customerAccountId, CustomerAccount customerAccount) {
		
		jdbcTemplate.update(CustomerAccountSqlQuery.SQL_UPDATE_BY_ID.query,
				new Object[] { 
							customerAccount.getCustomerMoney(),
							customerAccountId,
				});
		
		

	}

	@Override
	public void saveAll(List<CustomerAccount> customerAccounts) {
		List<CustomerAccount> list = new ArrayList<>(customerAccounts);
		jdbcTemplate.batchUpdate(CustomerAccountSqlQuery.SQL_SAVE.query, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				CustomerAccount customerAccount = list.get(i);
				ps.setLong(1, customerAccount.getCustomerAccountId());
				ps.setDouble(2, customerAccount.getCustomerMoney());
				
			}

			@Override
			public int getBatchSize() {
				return customerAccounts.size();
			}
		});
	}

}
