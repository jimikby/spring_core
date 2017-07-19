package com.epam.theatre.dao;

import java.util.List;

import com.epam.theatre.domain.CustomerAccount;

public interface CustomerAccountDao extends AbstractDomainObjectDao<CustomerAccount> {

	void saveAll(List<CustomerAccount> customerAccounts);

	void updateById(Long customerAccountId, CustomerAccount customerAccount);

}
