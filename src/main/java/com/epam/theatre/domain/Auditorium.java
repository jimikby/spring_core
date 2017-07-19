package com.epam.theatre.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.springframework.stereotype.Component;

@Component
public class Auditorium {

	private Long auditoriumId;

	private String name;

	private Long numberOfSeats;

	private Set<Long> vipSeats = Collections.emptySet();

	public Auditorium() {

	}

	public long countVipSeats(Collection<Long> seats) {
		return seats.stream().filter(seat -> vipSeats.contains(seat)).count();
	}

	public Long getAuditoriumId() {
		return auditoriumId;
	}

	public void setAuditoriumId(Long auditoriumId) {
		this.auditoriumId = auditoriumId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(long numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public Set<Long> getAllSeats() {
		return LongStream.range(1, numberOfSeats + 1).boxed().collect(Collectors.toSet());
	}

	public Set<Long> getVipSeats() {
		return vipSeats;
	}

	public void setVipSeats(Set<Long> vipSeats) {
		this.vipSeats = vipSeats;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Auditorium other = (Auditorium) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Auditorium [auditoriumId=");
		builder.append(auditoriumId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", numberOfSeats=");
		builder.append(numberOfSeats);
		builder.append(", vipSeats=");
		builder.append(vipSeats);
		builder.append("]");
		return builder.toString();
	}

}
