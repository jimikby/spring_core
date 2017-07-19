package com.epam.theatre.dao;

import java.util.List;

import com.epam.theatre.domain.Customer;

public interface CustomerDao extends AbstractDomainObjectDao<Customer> {

	Customer getUserByEmail(String email);

	Customer takeByEmailPassword(String email, String password);

	void updateById(Long customerId, Customer customer);

	void saveAll(List<Customer> users);

}
