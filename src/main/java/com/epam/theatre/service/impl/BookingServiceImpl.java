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
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.epam.theatre.domain.Auditorium;
import com.epam.theatre.domain.Event;
import com.epam.theatre.domain.EventSchedule;
import com.epam.theatre.domain.Ticket;
import com.epam.theatre.domain.Customer;
import com.epam.theatre.domain.CustomerAccount;
import com.epam.theatre.service.AuditoriumService;
import com.epam.theatre.service.BookingService;
import com.epam.theatre.service.CustomerAccountService;
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
	private CustomerService customerService;

	@Autowired
	private TicketService ticketService;

	@Autowired
	private CustomerAccountService customerAccountService;

	@Autowired
	private PlatformTransactionManager txManager;

	@Override
	public Set<Ticket> takeTicketsWithPrices(Long eventScheduleId, Set<Long> seats, Long userId) {

		Set<Ticket> tickets = new HashSet<>();

		EventSchedule eventSchedule = eventScheduleService.getById(eventScheduleId);

		Event event = eventService.getById(eventSchedule.getEventId());

		Auditorium auditorium = auditoriumService.getById(eventSchedule.getAuditoriumId());

		Customer user;

		if (userId != null) {

			user = customerService.getById(userId);
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

			userId = customerService.save(user);

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
	public void bookTickets(Set<Ticket> tickets, Long customerId) {

		CustomerAccount customerAccount = customerAccountService.getById(customerId);

		double money = customerAccount.getCustomerMoney();

		double costTicket = calcTicketsCost(tickets);

		double accountMoney = money - costTicket;

		customerAccount.setCustomerMoney(accountMoney);

		LOGGER.log(Level.INFO, "Customer account " + customerAccount + " have money");

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();

		def.setName("SomeTxName");
		
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

		TransactionStatus status = txManager.getTransaction(def);
		ticketService.saveAll(tickets);
		customerAccountService.updateById(customerAccount.getCustomerAccountId(), customerAccount);

		if (accountMoney >= 0.0) {

			txManager.commit(status);
			LOGGER.log(Level.INFO, "COMMIT Customer account " + customerAccount + " have money");
		} else {

			txManager.rollback(status);
			LOGGER.log(Level.INFO, "ROLLBACK Customer account " + customerAccount + " doesn't have money");

		}

	}

	private Double calcTicketsCost(Set<Ticket> tickets) {

		Double value = 0.0;

		for (Ticket ticket : tickets) {

			value = value + ticket.getTicketCost();

		}

		return value;
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
