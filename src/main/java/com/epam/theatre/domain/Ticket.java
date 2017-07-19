package com.epam.theatre.domain;

public class Ticket implements DomainObject {

	private Long ticketId;

	private Long eventScheduleId;

	private Long seat;

	private Double ticketCost;

	private Long userId;

	private Double discount;

	public Ticket() {
	}

	public Ticket(Long eventScheduleId, Long seat, Double ticketCost, Long userId, Double discount) {

		this.eventScheduleId = eventScheduleId;
		this.seat = seat;
		this.ticketCost = ticketCost;
		this.userId = userId;
		this.discount = discount;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public Long getEventScheduleId() {
		return eventScheduleId;
	}

	public void setEventScheduleId(Long eventScheduleId) {
		this.eventScheduleId = eventScheduleId;
	}

	public Long getSeat() {
		return seat;
	}

	public void setSeat(Long seat) {
		this.seat = seat;
	}

	public Double getTicketCost() {
		return ticketCost;
	}

	public void setTicketCost(Double ticketCost) {
		this.ticketCost = ticketCost;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((discount == null) ? 0 : discount.hashCode());
		result = prime * result + ((eventScheduleId == null) ? 0 : eventScheduleId.hashCode());
		result = prime * result + ((seat == null) ? 0 : seat.hashCode());
		result = prime * result + ((ticketCost == null) ? 0 : ticketCost.hashCode());
		result = prime * result + ((ticketId == null) ? 0 : ticketId.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		Ticket other = (Ticket) obj;
		if (discount == null) {
			if (other.discount != null)
				return false;
		} else if (!discount.equals(other.discount))
			return false;
		if (eventScheduleId == null) {
			if (other.eventScheduleId != null)
				return false;
		} else if (!eventScheduleId.equals(other.eventScheduleId))
			return false;
		if (seat == null) {
			if (other.seat != null)
				return false;
		} else if (!seat.equals(other.seat))
			return false;
		if (ticketCost == null) {
			if (other.ticketCost != null)
				return false;
		} else if (!ticketCost.equals(other.ticketCost))
			return false;
		if (ticketId == null) {
			if (other.ticketId != null)
				return false;
		} else if (!ticketId.equals(other.ticketId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Ticket [ticketId=");
		builder.append(ticketId);
		builder.append(", eventScheduleId=");
		builder.append(eventScheduleId);
		builder.append(", seat=");
		builder.append(seat);
		builder.append(", ticketCost=");
		builder.append(ticketCost);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", discount=");
		builder.append(discount);
		builder.append("]\n");
		return builder.toString();
	}

}
