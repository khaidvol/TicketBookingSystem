package integration;

import storage.BookingStorage;
import facade.BookingFacade;
import facade.BookingFacadeImpl;
import model.Event;
import model.Ticket;
import model.User;
import model.implementation.EventImpl;
import model.implementation.UserImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static model.Ticket.Category.*;

public class IntegrationTest {

  ClassPathXmlApplicationContext context;
  BookingFacade booking;

  @Before
  public void setUp() {

    context = new ClassPathXmlApplicationContext("applicationContext.xml");
    booking = context.getBean(BookingFacadeImpl.class);
  }

  @Test
  public void testIntegrationScenario() {
    User userBob = booking.createUser(new UserImpl("Bob", "bob@gmail.com"));
    User userMike = booking.createUser(new UserImpl("Mike", "mike@gmail.com"));
    User userJack = booking.createUser(new UserImpl("Jack", "jack@gmail.com"));

    Event eventDisco = booking.createEvent(new EventImpl("Disco", new Date()));

    Ticket ticket1 = booking.bookTicket(userBob.getId(), eventDisco.getId(), 1, STANDARD);
    Ticket ticket2 = booking.bookTicket(userBob.getId(), eventDisco.getId(), 2, PREMIUM);
    Ticket ticket3 = booking.bookTicket(userBob.getId(), eventDisco.getId(), 3, BAR);

    Ticket ticket4 = booking.bookTicket(userMike.getId(), eventDisco.getId(), 4, STANDARD);
    Ticket ticket5 = booking.bookTicket(userMike.getId(), eventDisco.getId(), 5, PREMIUM);
    Ticket ticket6 = booking.bookTicket(userMike.getId(), eventDisco.getId(), 6, BAR);

    Ticket ticket7 = booking.bookTicket(userJack.getId(), eventDisco.getId(), 7, STANDARD);
    Ticket ticket8 = booking.bookTicket(userJack.getId(), eventDisco.getId(), 8, PREMIUM);
    Ticket ticket9 = booking.bookTicket(userJack.getId(), eventDisco.getId(), 9, BAR);

    List<Ticket> expectedTicketsByUser = Arrays.asList(ticket1, ticket2, ticket3);
    List<Ticket> actualTicketsByUser = booking.getBookedTickets(userBob, 1, 1);

    List<Ticket> expectedTicketsByEvent =
        Arrays.asList(
            ticket1, ticket2, ticket3, ticket7, ticket8, ticket9, ticket4, ticket5, ticket6);
    List<Ticket> actualTicketsByEvent = booking.getBookedTickets(eventDisco, 1, 1);

    Assert.assertEquals(expectedTicketsByUser, actualTicketsByUser);
    Assert.assertEquals(expectedTicketsByEvent, actualTicketsByEvent);
    Assert.assertTrue(booking.cancelTicket(ticket3.getId()));
    Assert.assertTrue(booking.cancelTicket(ticket2.getId()));
    Assert.assertTrue(booking.cancelTicket(ticket1.getId()));
  }

  @After
  public void cleanUp() {
    BookingStorage bookingStorage = context.getBean(BookingStorage.class);
    bookingStorage.getUsers().clear();
    bookingStorage.getTickets().clear();
    bookingStorage.getEvents().clear();
  }
}
