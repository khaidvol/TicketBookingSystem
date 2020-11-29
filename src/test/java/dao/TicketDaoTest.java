package dao;

import model.EventImpl;
import model.Ticket;
import model.TicketImpl;
import model.UserImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TicketDaoTest {

  ClassPathXmlApplicationContext context;
  TicketDao ticketDao;

  public TicketDaoTest() {}

  @Before
  public void setUp() {
    context = new ClassPathXmlApplicationContext("applicationContext.xml");
    ticketDao = context.getBean(TicketDao.class);
  }

  @Test
  public void createTicketTest() {
    Ticket ticket = Mockito.mock(Ticket.class);
    Mockito.when(ticket.getId()).thenReturn(1L);
    Assert.assertEquals(ticket, ticketDao.createTicket(ticket));
  }

  @Test
  public void createTicketWithTheSameIdTest() {
    Ticket ticket1 = Mockito.mock(Ticket.class);
    Mockito.when(ticket1.getId()).thenReturn(1L);
    Mockito.when(ticket1.getPlace()).thenReturn(1);
    Ticket ticket2 = Mockito.mock(Ticket.class);
    Mockito.when(ticket2.getId()).thenReturn(1L);
    Mockito.when(ticket2.getPlace()).thenReturn(2);
    Assert.assertEquals(ticket1, ticketDao.createTicket(ticket1));
    Assert.assertNull(ticketDao.createTicket(ticket2));
  }

  @Test(expected = IllegalStateException.class)
  public void createTicketWithTheSamePlaceTest() throws Exception {
    Ticket ticket1 = Mockito.mock(Ticket.class);
    Mockito.when(ticket1.getId()).thenReturn(1L);
    Ticket ticket2 = Mockito.mock(Ticket.class);
    Mockito.when(ticket2.getId()).thenReturn(2L);
    ticketDao.createTicket(ticket1);
    ticketDao.createTicket(ticket2);
  }

  @Test
  public void deleteTicketTest() {
    Ticket ticket = Mockito.mock(Ticket.class);
    Mockito.when(ticket.getId()).thenReturn(1L);
    ticketDao.createTicket(ticket);
    Assert.assertTrue(ticketDao.deleteTicket(ticket.getId()));
  }

  @Test
  public void deleteNotExistingTicketTest() {
    Ticket ticket = Mockito.mock(Ticket.class);
    Mockito.when(ticket.getId()).thenReturn(1L);
    Assert.assertFalse(ticketDao.deleteTicket(ticket.getId()));
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
