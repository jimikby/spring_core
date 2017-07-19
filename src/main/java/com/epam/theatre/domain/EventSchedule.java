package com.epam.theatre.domain;

import java.time.LocalDateTime;

public class EventSchedule implements DomainObject {

	private Long eventScheudleId;

	private LocalDateTime eventDate;

	private Long eventId;

	private Long auditoriumId;

	public Long getEventScheduleId() {
		return eventScheudleId;
	}

	public void setEventScheudleId(Long eventScheudleId) {
		this.eventScheudleId = eventScheudleId;
	}

	public LocalDateTime getEventDate() {
		return eventDate;
	}

	public void setEventDate(LocalDateTime eventDate) {
		this.eventDate = eventDate;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Long getAuditoriumId() {
		return auditoriumId;
	}

	public void setAuditoriumId(Long auditoriumId) {
		this.auditoriumId = auditoriumId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((auditoriumId == null) ? 0 : auditoriumId.hashCode());
		result = prime * result + ((eventDate == null) ? 0 : eventDate.hashCode());
		result = prime * result + ((eventId == null) ? 0 : eventId.hashCode());
		result = prime * result + ((eventScheudleId == null) ? 0 : eventScheudleId.hashCode());
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
		EventSchedule other = (EventSchedule) obj;
		if (auditoriumId == null) {
			if (other.auditoriumId != null)
				return false;
		} else if (!auditoriumId.equals(other.auditoriumId))
			return false;
		if (eventDate == null) {
			if (other.eventDate != null)
				return false;
		} else if (!eventDate.equals(other.eventDate))
			return false;
		if (eventId == null) {
			if (other.eventId != null)
				return false;
		} else if (!eventId.equals(other.eventId))
			return false;
		if (eventScheudleId == null) {
			if (other.eventScheudleId != null)
				return false;
		} else if (!eventScheudleId.equals(other.eventScheudleId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EventSchedule [eventScheudleId=");
		builder.append(eventScheudleId);
		builder.append(", eventDate=");
		builder.append(eventDate);
		builder.append(", eventId=");
		builder.append(eventId);
		builder.append(", auditoriumId=");
		builder.append(auditoriumId);
		builder.append("]\n");
		return builder.toString();
	}

}
