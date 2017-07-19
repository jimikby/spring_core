package com.epam.theatre.service;

import java.util.List;

import com.epam.theatre.domain.CustomerAccount;

public interface CustomerAccountService extends AbstractDomainObjectService<CustomerAccount> {

	void saveAll(List<CustomerAccount> customerAccounts);

	void updateById(Long customerAccountId, CustomerAccount customerAccount);

}
