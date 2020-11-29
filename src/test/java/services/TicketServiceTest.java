package services;

import dao.BookingStorage;
import model.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TicketServiceTest {

  ClassPathXmlApplicationContext context;
  TicketService ticketService;

  public TicketServiceTest() {}

  @Before
  public void setUp() {
    context = new ClassPathXmlApplicationContext("applicationContext.xml");
    ticketService = context.getBean(TicketService.class);
  }

  @Test
  public void bookTicketTest() {
    Assert.assertNotNull(ticketService.bookTicket(2, 2, 2, Ticket.Category.STANDARD));
  }

  @Test
  public void getTicketsByUserTest() {
    ticketService.bookTicket(333, 333, 333, Ticket.Category.STANDARD);
    User user = Mockito.mock(User.class);
    Mockito.when(user.getId()).thenReturn(333L);
    Assert.assertEquals(1, ticketService.getBookedTickets(user, 1, 1).size());
  }

  @Test
  public void getTicketsByEventTest() {
    ticketService.bookTicket(666, 666, 666, Ticket.Category.PREMIUM);
    Event event = Mockito.mock(Event.class);
    Mockito.when(event.getId()).thenReturn(666L);
    Assert.assertEquals(1, ticketService.getBookedTickets(event, 1, 1).size());
  }

  @Test
  public void cancelTicketTest() {
    Ticket ticket = ticketService.bookTicket(999, 999, 999, Ticket.Category.BAR);
    Assert.assertTrue(ticketService.cancelTicket(ticket.getId()));
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
