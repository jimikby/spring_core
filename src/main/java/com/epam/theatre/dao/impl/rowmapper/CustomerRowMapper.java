package com.epam.theatre.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.RowMapper;

import com.epam.theatre.dao.SqlFields;
import com.epam.theatre.domain.Customer;

public class CustomerRowMapper implements RowMapper<Customer> {

	@Override
	public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {

		Customer user = new Customer();
		user.setCustomerId(rs.getLong(SqlFields.CUSTOMER_ID.name()));
		user.setFirstName(rs.getString(SqlFields.FIRST_NAME.name()));
		user.setLastName(rs.getString(SqlFields.LAST_NAME.name()));
		
		user.setRole (
				Pattern.compile(",")
					   .splitAsStream(
							   rs.getString(SqlFields.ROLE.name())
					   )
			.collect(Collectors.toSet())
			
		  );
		
		user.setBirthDay(rs.getTimestamp(SqlFields.BIRTHDAY.name()).toLocalDateTime().toLocalDate());
		user.setEmail(rs.getString(SqlFields.EMAIL.name()));
		user.setPassword(rs.getString(SqlFields.PASSWORD.name()));

		return user;
	}

}
