package com.epam.theatre.aspect;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.theatre.domain.Counter;
import com.epam.theatre.domain.Event;
import com.epam.theatre.domain.Ticket;
import com.epam.theatre.service.CounterService;
import com.epam.theatre.service.EventScheduleService;
import com.epam.theatre.service.EventService;
import com.epam.theatre.service.TicketService;

@Aspect
@Component
public class CounterAspect {

	private static final Logger LOGGER = LogManager.getLogger(CounterAspect.class);

	private static final String GET_TICKET_PRICE = "GET_TICKET_PRICE";
	private static final String GET_BOOK_TICKET = "GET_BOOK_TICKET";
	private static final String EVENT_GET_BY_NAME = "EVENT_GET_BY_NAME";

	@Autowired
	private CounterService counterService;

	@Autowired
	private EventService eventService;

	@Autowired
	private EventScheduleService eventScheduleService;

	@Autowired
	private TicketService ticketService;

	@Pointcut("execution(* com.epam.theatre.service.EventService.getByName(..))")
	private void eventServiceGetByNameMethod() {
	}

	@AfterReturning(pointcut = "eventServiceGetByNameMethod()", returning = "event")
	public void countEventGetByName(Event event) {

		Counter counter = counterService.takeByClassNameAndClassId(event.getClass().getSimpleName(), EVENT_GET_BY_NAME,
				event.getEventId());
		if (counter == null) {

			counter = new Counter();
			counter.setCounterName(EVENT_GET_BY_NAME);
			counter.setCounterValue(0L);
			counter.setTypeName(event.getClass().getSimpleName());
			counter.setTypeId(event.getEventId());

			Long counterId = counterService.save(counter);
			counter.setCounterId(counterId);

		}

		counterService.update(counter.getCounterId(), counter.getCounterValue() + 1);

		LOGGER.log(Level.INFO, "@AfterReturning " + event.getEventName() + " " + counter.getCounterName() + " "
				+ counterService.getById(counter.getCounterId()).getCounterValue());

	}

	@Pointcut("execution(* com.epam.theatre.service.BookingService.takeTicketsWithPrices(..))")
	private void bookingServiceGetTicketPricesMethod() {
	}

	@AfterReturning(pointcut = "bookingServiceGetTicketPricesMethod()")
	public void countGetTicketsPriceAll() {

		LOGGER.log(Level.INFO, "@AfterReturning GET_TICKET_PRICES (The All quantity) "
				+ counterService.takeAllCountersValueByClassName(GET_TICKET_PRICE));

	}

	@AfterReturning(pointcut = "bookingServiceGetTicketPricesMethod()", returning = "tickets")
	public void countGetTicketPrices(Set<Ticket> tickets) {

		Event event = null;

		for (Ticket ticket : tickets) {

			event = eventService.getById(eventScheduleService.getById(ticket.getEventScheduleId()).getEventId());

			Counter counter = counterService.takeByClassNameAndClassId(event.getClass().getSimpleName(),
					GET_TICKET_PRICE, event.getEventId());

			if (counter == null) {

				counter = new Counter();
				counter.setCounterName(GET_TICKET_PRICE);
				counter.setCounterValue(0L);
				counter.setTypeName(event.getClass().getSimpleName());
				counter.setTypeId(event.getEventId());

				Long counterId = counterService.save(counter);
				counter.setCounterId(counterId);

			}

			counter.setCounterValue(counter.getCounterValue() + 1);
			counterService.update(counter.getCounterId(), counter.getCounterValue());

			LOGGER.log(Level.INFO, "@AfterReturning " + event.getEventName() + " " + counter.getCounterName() + " "
					+ counterService.getById(counter.getCounterId()).getCounterValue());

		}

	}

	@AfterReturning(pointcut = "bookingServiceBookTicketMethod()")
	public void countBookTicketAll() {

		LOGGER.log(Level.INFO, "@AfterReturning GET_TICKET_PRICES (The All quantity) "
				+ counterService.takeAllCountersValueByClassName(GET_TICKET_PRICE));

	}

	@Pointcut("execution(* com.epam.theatre.service.BookingService.bookTickets(..))")
	private void bookingServiceBookTicketMethod() {
	}

	@AfterReturning(pointcut = "bookingServiceBookTicketMethod()", returning = "ticketsId")
	public void countBookTicket(Set<Long> ticketsId) {

		Map<Long, Counter> eventCounters = new HashMap<>();

		for (Long ticketId : ticketsId) {

			Event event = eventService.getById(
					eventScheduleService.getById((ticketService.getById(ticketId).getEventScheduleId())).getEventId());

			Counter counter = counterService.takeByClassNameAndClassId(event.getClass().getSimpleName(),
					GET_BOOK_TICKET, event.getEventId());

			if (counter == null) {

				counter = new Counter();
				counter.setCounterName(GET_BOOK_TICKET);
				counter.setCounterValue(0L);
				counter.setTypeName(event.getClass().getSimpleName());
				counter.setTypeId(event.getEventId());

				Long counterId = counterService.save(counter);
				counter.setCounterId(counterId);

			}

			eventCounters.put(counter.getCounterValue(), counter);

		}

		for (Counter counter : eventCounters.values()) {

			counter.setCounterValue(counter.getCounterValue() + 1);
			counterService.update(counter.getCounterId(), counter.getCounterValue());

			LOGGER.log(Level.INFO, "@AfterReturning" + " " + counter.getCounterName() + " "
					+ counterService.getById(counter.getCounterId()).getCounterValue());

			counterService.update(counter.getCounterId(), counter.getCounterValue());

		}

	}
}