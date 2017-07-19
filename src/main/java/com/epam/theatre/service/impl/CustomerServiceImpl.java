package com.epam.theatre.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.theatre.dao.CustomerDao;
import com.epam.theatre.domain.Customer;
import com.epam.theatre.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDao customerDao;

	@Override
	public Long save(Customer user) {
		return customerDao.save(user);
	}

	@Override
	public void remove(Long userId) {
		customerDao.remove(userId);

	}

	@Override
	public Customer getById(Long userId) {
		return customerDao.getById(userId);
	}

	@Override
	public List<Customer> getAll() {
		return customerDao.getAll();
	}

	@Override
	public Customer takeByEmail(String email) {
		return customerDao.getUserByEmail(email);
	}

	@Override
	public Customer takeByEmailPassword(String email, String password) {
		return customerDao.takeByEmailPassword(email, password);
	}

	@Override
	public void updateById(Long customerId, Customer customer) {
		customerDao.updateById(customerId, customer);

	}

	@Override
	public void saveAll(List<Customer> users) {
		customerDao.saveAll(users);
		
	}

}
