package com.epam.theatre.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.epam.theatre.dao.SqlFields;
import com.epam.theatre.domain.CustomerAccount;

public class CustomerAccountRowMapper implements RowMapper<CustomerAccount> {

	@Override
	public CustomerAccount mapRow(ResultSet rs, int rowNum) throws SQLException {

		return new CustomerAccount() {
			{
				setCustomerAccountId(rs.getLong(SqlFields.CUSTOMER_ACCOUNT_ID.name()));
				setCustomerId(rs.getLong(SqlFields.CUSTOMER_ID.name()));
				setCustomerMoney(rs.getDouble(SqlFields.CUSTOMER_MONEY.name()));
			}
		};
	}
}