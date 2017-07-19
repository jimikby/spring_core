package com.epam.theatre.service;

import java.util.List;

import com.epam.theatre.domain.Customer;

public interface CustomerService extends AbstractDomainObjectService<Customer> {

	Customer takeByEmail(String email);

	Customer takeByEmailPassword(String email, String password);

	void updateById(Long customerId, Customer customer);

	void saveAll(List<Customer> users);

}
