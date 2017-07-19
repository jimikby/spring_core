package com.epam.theatre.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.theatre.dao.CustomerAccountDao;
import com.epam.theatre.domain.CustomerAccount;
import com.epam.theatre.service.CustomerAccountService;

@Service
public class CustomerAccountServiceImpl implements CustomerAccountService {

	@Autowired
	private CustomerAccountDao customerAccountDao;

	@Override
	public Long save(CustomerAccount customerAccount) {
		return customerAccountDao.save(customerAccount);
	}

	@Override
	public void remove(Long customerAccountId) {
		customerAccountDao.remove(customerAccountId);

	}

	@Override
	public CustomerAccount getById(Long customerAccountId) {
		return customerAccountDao.getById(customerAccountId);
	}

	@Override
	public List<CustomerAccount> getAll() {
		return customerAccountDao.getAll();
	}

	@Override
	public void updateById(Long customerAccountId, CustomerAccount customer) {
		customerAccountDao.updateById(customerAccountId, customer);

	}

	@Override
	public void saveAll(List<CustomerAccount> customerAccounts) {
		customerAccountDao.saveAll(customerAccounts);
		
	}

}
