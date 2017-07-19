package com.epam.theatre.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.epam.theatre.domain.Auditorium;
import com.epam.theatre.domain.Event;
import com.epam.theatre.domain.EventSchedule;
import com.epam.theatre.domain.Ticket;
import com.epam.theatre.domain.Customer;
import com.epam.theatre.service.AuditoriumService;
import com.epam.theatre.service.BookingService;
import com.epam.theatre.service.DiscountService;
import com.epam.theatre.service.EventScheduleService;
import com.epam.theatre.service.EventService;
import com.epam.theatre.service.TicketService;
import com.epam.theatre.service.CustomerService;

@Service
public class BookingServiceImpl implements BookingService {

	private static final Logger LOGGER = LogManager.getLogger(BookingServiceImpl.class);

	@Value("${vip.increase}")
	private Double vipIncrease;

	@Value("${rating.low}")
	private Integer ratingLow;

	@Value("${rating.mid}")
	private Integer ratingMid;

	@Value("${rating.high}")
	private Integer ratingHigh;

	@Autowired
	private DiscountService discountService;

	@Autowired
	private EventService eventService;

	@Autowired
	private EventScheduleService eventScheduleService;

	@Autowired
	private AuditoriumService auditoriumService;

	@Autowired
	private CustomerService userService;

	@Autowired
	private TicketService ticketService;

	@Override
	public Set<Ticket> takeTicketsWithPrices(Long eventScheduleId, Set<Long> seats, Long userId) {

		Set<Ticket> tickets = new HashSet<>();

		EventSchedule eventSchedule = eventScheduleService.getById(eventScheduleId);

		Event event = eventService.getById(eventSchedule.getEventId());

		Auditorium auditorium = auditoriumService.getById(eventSchedule.getAuditoriumId());

		Customer user;

		if (userId != null) {

			user = userService.getById(userId);
		} else {

			user = new Customer() {
				{
					setRole((Set<String>) new HashSet<String>());
					
					setBirthDay(LocalDate.now().plusMonths(3));
					setEmail(LocalDateTime.now() + "@" + LocalDateTime.now() + ".ru");
					setFirstName("none");
					setLastName("none");
					setPassword("none");

				}
			};
	
			userId = userService.save(user);	

		}

		double price = priceRating(event, event.getBasePrice());

		double priceModified = price;

		long userCountTickets = ticketService.getTicketsQuantityByUserId(user.getCustomerId());

		long ordinalNumberTicket = userCountTickets;

		double discountCost = 0.0;

		for (Long seat : seats) {

			if (auditorium != null) {

				priceModified = price;

				if (auditorium.getVipSeats().contains(seat)) {

					priceModified = price * vipIncrease / 100;

					LOGGER.log(Level.INFO, "VIP Seat: #" + seat);

				}

			}

			ordinalNumberTicket++;

			Double discount = discountService.getDiscount(user, event, eventSchedule.getEventDate().toLocalDate(),
					ordinalNumberTicket);

			if (discount != null) {

				discountCost = priceModified * discount / 100;

				priceModified -= discountCost;

				LOGGER.log(Level.INFO, "Discount ticket #" + seat + ", discount type: " + discount);

			}

			tickets.add(new Ticket(eventScheduleId, seat, priceModified, userId, discount));

		}

		return tickets;
	}

	@Override
	public Set<Long> bookTickets(Set<Ticket> tickets) {

		Set<Long> ticketsId = new HashSet<>();

		ticketService.saveAll(tickets);

		return ticketsId;
	}

	@Override
	public Set<Long> checkSeats(Set<Long> seats, Long eventScheduleId) {

		Set<Long> newSeats = new HashSet<>();
		
		System.out.println(eventScheduleId);

		Auditorium auditorium = auditoriumService
				.getById(eventScheduleService.getById(eventScheduleId).getAuditoriumId());

		long numberOfSeats = auditorium.getNumberOfSeats();

		for (Long seat : seats) {

			if (seat <= numberOfSeats && seat != 0) {

				if (!ticketService.checkSeat(seat, eventScheduleId)) {

					newSeats.add(seat);

				} else {

					LOGGER.log(Level.WARN, "The seat #" + seat + " already has been booked!");
				}

			} else {

				LOGGER.log(Level.WARN, "The seat #" + seat + " doesn't exist(1-" + numberOfSeats + ")!");
			}

		}

		return newSeats;
	}

	private Double priceRating(Event event, Double price) {
		switch (event.getRating()) {

		case HIGH:
			price = event.getBasePrice() * ratingHigh / 100;
			break;
		case LOW:
			price = event.getBasePrice() * ratingLow / 100;
			break;
		case MID:
			price = event.getBasePrice() * ratingMid / 100;
			break;
		default:
			break;

		}
		return price;
	}
}
