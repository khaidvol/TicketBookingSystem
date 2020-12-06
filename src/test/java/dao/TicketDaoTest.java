package dao;

import dao.implementation.TicketDao;
import model.Ticket;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import storage.BookingStorage;

public class TicketDaoTest {

  ApplicationContext context;
  BookingStorage bookingStorage;
  Dao<Ticket> ticketDao;
  Ticket ticket;

  public TicketDaoTest() {}

  @Before
  public void setUp() {

    context = new ClassPathXmlApplicationContext("applicationContextTest.xml");
    bookingStorage = context.getBean("testingBookingStorage", BookingStorage.class);
    ticketDao = context.getBean("testTicketDao", TicketDao.class);

    ticket = Mockito.mock(Ticket.class);
    Mockito.when(ticket.getId()).thenReturn(10L);
  }

  @Test
  public void createTest() {
    Assert.assertNull(ticketDao.create(ticket));
    Assert.assertEquals(ticket, ticketDao.read(ticket.getId()));
  }

  @Test
  public void readTest() {
    Assert.assertNull(ticketDao.create(ticket));
    Assert.assertEquals(ticket, ticketDao.read(ticket.getId()));
  }

  @Test
  public void readAllTest() {
    Assert.assertNull(ticketDao.create(ticket));
    Assert.assertNotNull(ticketDao.readAll());
  }

  @Test
  public void updateTest() {
    Assert.assertNull(ticketDao.create(ticket));
    Assert.assertEquals(ticket, ticketDao.update(ticket));
  }

  @Test
  public void deleteTest() {
    Assert.assertNull(ticketDao.create(ticket));
    Assert.assertEquals(ticket, ticketDao.delete(ticket.getId()));
  }

  @Test
  public void getMaxIdWhenStorageNotEmptyTest() {
    Assert.assertNull(ticketDao.create(ticket));
    Assert.assertNotNull(ticketDao.getMaxId());
  }

  @Test
  public void getMaxIdWhenStorageIsEmptyTest() {
    bookingStorage.getTickets().clear();
    Assert.assertNotNull(ticketDao.getMaxId());
  }

  @After
  public void cleanUp() {
    bookingStorage.cleanStorage();
  }
}
