package integration;

import dao.BookingStorage;
import facade.BookingFacade;
import model.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.BookingService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class IntegrationTest {

  ClassPathXmlApplicationContext context;
  BookingFacade bookingService;

  @Before
  public void setUp() {

    context = new ClassPathXmlApplicationContext("applicationContext.xml");
    bookingService = context.getBean(BookingService.class);
  }

  @Test
  public void testIntegrationScenario() {
    User userBob = bookingService.createUser(new UserImpl("Bob", "bob@gmail.com"));
    User userMike = bookingService.createUser(new UserImpl("Mike", "mike@gmail.com"));
    User userJack = bookingService.createUser(new UserImpl("Jack", "jack@gmail.com"));

    Date date = new Date(System.currentTimeMillis());
    Event eventDisco = bookingService.createEvent(new EventImpl("Disco", date));

    Ticket ticket1 =
        bookingService.bookTicket(userBob.getId(), eventDisco.getId(), 1, Ticket.Category.STANDARD);
    Ticket ticket2 =
        bookingService.bookTicket(userBob.getId(), eventDisco.getId(), 2, Ticket.Category.PREMIUM);
    Ticket ticket3 =
        bookingService.bookTicket(userBob.getId(), eventDisco.getId(), 3, Ticket.Category.BAR);

    Ticket ticket4 =
        bookingService.bookTicket(
            userMike.getId(), eventDisco.getId(), 4, Ticket.Category.STANDARD);
    Ticket ticket5 =
        bookingService.bookTicket(userMike.getId(), eventDisco.getId(), 5, Ticket.Category.PREMIUM);
    Ticket ticket6 =
        bookingService.bookTicket(userMike.getId(), eventDisco.getId(), 6, Ticket.Category.BAR);

    Ticket ticket7 =
        bookingService.bookTicket(
            userJack.getId(), eventDisco.getId(), 7, Ticket.Category.STANDARD);
    Ticket ticket8 =
        bookingService.bookTicket(userJack.getId(), eventDisco.getId(), 8, Ticket.Category.PREMIUM);
    Ticket ticket9 =
        bookingService.bookTicket(userJack.getId(), eventDisco.getId(), 9, Ticket.Category.BAR);

    List<Ticket> expectedTicketsByUser = Arrays.asList(ticket1, ticket2, ticket3);
    List<Ticket> actualTicketsByUser = bookingService.getBookedTickets(userBob, 1, 1);
    List<Ticket> expectedTicketsByEvent =
        Arrays.asList(
            ticket1, ticket2, ticket3, ticket7, ticket8, ticket9, ticket4, ticket5, ticket6);
    List<Ticket> actualTicketsByEvent = bookingService.getBookedTickets(eventDisco, 1, 1);

    Assert.assertEquals(expectedTicketsByUser, actualTicketsByUser);
    Assert.assertEquals(expectedTicketsByEvent, actualTicketsByEvent);
    Assert.assertTrue(bookingService.cancelTicket(ticket3.getId()));
    Assert.assertTrue(bookingService.cancelTicket(ticket2.getId()));
    Assert.assertTrue(bookingService.cancelTicket(ticket1.getId()));
  }

  @After
  public void cleanUp() {
    BookingStorage bookingStorage = context.getBean(BookingStorage.class);
    bookingStorage.getUsers().clear();
    bookingStorage.getTickets().clear();
    bookingStorage.getEvents().clear();
    UserImpl.setIdCounter(1);
    EventImpl.setIdCounter(1);
    TicketImpl.setIdCounter(1);
  }
}
