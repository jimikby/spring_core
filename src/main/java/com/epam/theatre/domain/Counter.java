package com.epam.theatre.domain;

public class Counter implements DomainObject {

	private Long counterId;

	private String typeName;

	private Long typeId;

	private String counterName;

	private Long counterValue;

	public Long getCounterId() {
		return counterId;
	}

	public void setCounterId(Long counterId) {
		this.counterId = counterId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getCounterName() {
		return counterName;
	}

	public void setCounterName(String counterName) {
		this.counterName = counterName;
	}

	public Long getCounterValue() {
		return counterValue;
	}

	public void setCounterValue(Long counterValue) {
		this.counterValue = counterValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((counterValue == null) ? 0 : counterValue.hashCode());
		result = prime * result + ((counterId == null) ? 0 : counterId.hashCode());
		result = prime * result + ((counterName == null) ? 0 : counterName.hashCode());
		result = prime * result + ((typeId == null) ? 0 : typeId.hashCode());
		result = prime * result + ((typeName == null) ? 0 : typeName.hashCode());
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
		Counter other = (Counter) obj;
		if (counterValue == null) {
			if (other.counterValue != null)
				return false;
		} else if (!counterValue.equals(other.counterValue))
			return false;
		if (counterId == null) {
			if (other.counterId != null)
				return false;
		} else if (!counterId.equals(other.counterId))
			return false;
		if (counterName == null) {
			if (other.counterName != null)
				return false;
		} else if (!counterName.equals(other.counterName))
			return false;
		if (typeId == null) {
			if (other.typeId != null)
				return false;
		} else if (!typeId.equals(other.typeId))
			return false;
		if (typeName == null) {
			if (other.typeName != null)
				return false;
		} else if (!typeName.equals(other.typeName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Counter [counterId=");
		builder.append(counterId);
		builder.append(", typeName=");
		builder.append(typeName);
		builder.append(", typeId=");
		builder.append(typeId);
		builder.append(", counterName=");
		builder.append(counterName);
		builder.append(", counterValue=");
		builder.append(counterValue);
		builder.append("]\n");
		return builder.toString();
	}

}
