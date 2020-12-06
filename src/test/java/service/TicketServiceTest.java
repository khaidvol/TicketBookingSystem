package service;

import model.Event;
import model.Ticket;
import model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import storage.BookingStorage;

public class TicketServiceTest {

  ApplicationContext context;
  BookingStorage bookingStorage;
  TicketService ticketService;
  Ticket ticket;

  public TicketServiceTest() {}

  @Before
  public void setUp() {
    context = new ClassPathXmlApplicationContext("applicationContextTest.xml");
    ticketService = context.getBean(TicketService.class);
    bookingStorage = context.getBean("testingBookingStorage", BookingStorage.class);
    ticket = Mockito.mock(Ticket.class);
  }

  @Test
  public void bookTicketTest() {
    Assert.assertNotNull(ticketService.bookTicket(1, 1, 111, Ticket.Category.STANDARD));
  }

  @Test(expected = IllegalStateException.class)
  public void bookTicketForAlreadyBookedPlaceTest() {
    ticketService.bookTicket(1, 1, 1, Ticket.Category.STANDARD);
  }

  @Test
  public void getTicketsByUserTest() {
    User user = Mockito.mock(User.class);
    Mockito.when(user.getId()).thenReturn(1L);
    Assert.assertEquals(3, ticketService.getBookedTickets(user, 1, 1).size());
  }

  @Test
  public void getTicketsByEventTest() {
    Event event = Mockito.mock(Event.class);
    Mockito.when(event.getId()).thenReturn(1L);
    Assert.assertEquals(3, ticketService.getBookedTickets(event, 1, 1).size());
  }

  @Test
  public void cancelTicketTest() {
    Assert.assertTrue(ticketService.cancelTicket(1));
  }

  @After
  public void cleanUp() {
    bookingStorage.cleanStorage();
  }
}
