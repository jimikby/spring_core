package com.epam.theatre.domain;

public class CustomerAccount implements DomainObject {
	
	private Long customerAccountId;
	private Long customerId;
	private Double customerMoney;
	
	public Long getCustomerAccountId() {
		return customerAccountId;
	}
	public void setCustomerAccountId(Long customerAccountId) {
		this.customerAccountId = customerAccountId;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Double getCustomerMoney() {
		return customerMoney;
	}
	public void setCustomerMoney(Double customerMoney) {
		this.customerMoney = customerMoney;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerAccountId == null) ? 0 : customerAccountId.hashCode());
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((customerMoney == null) ? 0 : customerMoney.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerAccount other = (CustomerAccount) obj;
		if (customerAccountId == null) {
			if (other.customerAccountId != null)
				return false;
		} else if (!customerAccountId.equals(other.customerAccountId))
			return false;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		if (customerMoney == null) {
			if (other.customerMoney != null)
				return false;
		} else if (!customerMoney.equals(other.customerMoney))
			return false;
		return true;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CustomerAccount [customerAccountId=");
		builder.append(customerAccountId);
		builder.append(", customerId=");
		builder.append(customerId);
		builder.append(", customerMoney=");
		builder.append(customerMoney);
		builder.append("]");
		return builder.toString();
	} 

}
