package com.epam.theatre.aspect;

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
import com.epam.theatre.domain.Ticket;
import com.epam.theatre.domain.Customer;
import com.epam.theatre.service.CounterService;
import com.epam.theatre.service.TicketService;
import com.epam.theatre.service.CustomerService;

@Aspect
@Component
public class DiscountAspect {

	private static final Logger LOGGER = LogManager.getLogger(DiscountAspect.class);

	@Autowired
	private CounterService counterService;

	@Autowired
	private CustomerService userService;

	@Autowired
	private TicketService ticketService;

	@Pointcut("execution(* com.epam.theatre.service.BookingService.bookTickets(..))")
	private void bookingServiceBookTickets() {
	}

	@AfterReturning(pointcut = "bookingServiceBookTickets() && args(tickets)")
	public void bookTickets(Set<Ticket> tickets) {

		Counter counter = null;

		for (Ticket ticket : tickets) {

			Customer user = userService.getById(ticket.getUserId());

			counter = counterService.takeByClassNameAndClassId(user.getClass().getSimpleName(),
					String.valueOf(ticket.getDiscount()), user.getCustomerId());

			if (counter == null) {

				counter = new Counter();
				counter.setCounterName(String.valueOf(ticket.getDiscount()));
				counter.setCounterValue(0L);
				counter.setTypeName(user.getClass().getSimpleName());
				counter.setTypeId(user.getCustomerId());

				Long counterId = counterService.save(counter);
				counter.setCounterId(counterId);

			}

			counter.setCounterValue(counter.getCounterValue() + 1);

			counterService.update(counter.getCounterId(), counter.getCounterValue());

			LOGGER.log(Level.INFO,
					"@AfterReturning Discount (The All quantity for all users) " + String.valueOf(ticket.getDiscount())
							+ " "
							+ counterService.takeAllCountersValueByClassName(String.valueOf(ticket.getDiscount())));
			LOGGER.log(Level.INFO,
					"@AfterReturning " + user.getFirstName() + " "
							+ ticketService.getTicketsQuantityByUserId(user.getCustomerId()) + " "
							+ counter.getCounterName() + " " + counter.getCounterValue());

		}

	}

}
