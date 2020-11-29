package services;

import dao.BookingStorage;
import facade.BookingFacade;
import model.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class BookingServiceTest {

  ClassPathXmlApplicationContext context;
  BookingFacade bookingService;

  public BookingServiceTest() {}

  @Before
  public void setUp() {

    context = new ClassPathXmlApplicationContext("applicationContext.xml");
    bookingService = context.getBean(BookingService.class);
  }

  @Test
  public void getEventByIdTest() {
    Event event = Mockito.mock(Event.class);
    bookingService.createEvent(event);
    Assert.assertEquals(event, bookingService.getEventById(event.getId()));
  }

  @Test
  public void getEventsByTitleTest() {
    Event event1 = new EventImpl("Event 1", new Date(System.currentTimeMillis()));
    Event event2 = new EventImpl("Event 2", new Date(System.currentTimeMillis()));
    List<Event> events = Arrays.asList(event1, event2);
    bookingService.createEvent(event1);
    bookingService.createEvent(event2);
    Assert.assertEquals(events, bookingService.getEventsByTitle("Event", 1, 1));
  }

  @Test
  public void getEventForDayTest() {
    Event event1 = new EventImpl("Event 1", new Date(System.currentTimeMillis()));
    Event event2 = new EventImpl("Event 2", new Date(System.currentTimeMillis()));
    List<Event> events = Arrays.asList(event1, event2);
    bookingService.createEvent(event1);
    bookingService.createEvent(event2);
    Assert.assertEquals(
        events, bookingService.getEventsForDay(new Date(System.currentTimeMillis()), 1, 1));
  }

  @Test
  public void createEventTest() {
    Event event = Mockito.mock(Event.class);
    Assert.assertEquals(event, bookingService.createEvent(event));
  }

  @Test
  public void updatedEventTest() {
    Event event = Mockito.mock(Event.class);
    bookingService.createEvent(event);
    Assert.assertEquals(event, bookingService.updateEvent(event));
  }

  @Test
  public void deleteEventTest() {
    Event event = Mockito.mock(Event.class);
    bookingService.createEvent(event);
    Assert.assertTrue(bookingService.deleteEvent(event.getId()));
  }

  @Test
  public void getUserByIdTest() {
    User user = Mockito.mock(User.class);
    bookingService.createUser(user);
    Assert.assertEquals(user, bookingService.getUserById(user.getId()));
  }

  @Test
  public void getUserByEmailTest() {
    User user = new UserImpl("Test", "test@gmail.com");
    bookingService.createUser(user);
    Assert.assertEquals(user, bookingService.getUserByEmail(user.getEmail()));
  }

  @Test
  public void getUsersByNameTest() {
    User user1 = new UserImpl("Test 1", "test1@gmail.com");
    User user2 = new UserImpl("Test 2", "test2@gmail.com");
    List<User> users = Arrays.asList(user1, user2);
    bookingService.createUser(user1);
    bookingService.createUser(user2);
    Assert.assertEquals(users, bookingService.getUsersByName("Test", 1, 1));
  }

  @Test
  public void createUserTest() {
    User user = Mockito.mock(User.class);
    Assert.assertEquals(user, bookingService.createUser(user));
  }

  @Test
  public void updatedUserTest() {
    User user = Mockito.mock(User.class);
    bookingService.createUser(user);
    Assert.assertEquals(user, bookingService.updateUser(user));
  }

  @Test
  public void deleteUserTest() {
    User user = Mockito.mock(User.class);
    bookingService.createUser(user);
    Assert.assertTrue(bookingService.deleteUser(user.getId()));
  }

  @Test
  public void bookTicketTest() {
    Assert.assertNotNull(bookingService.bookTicket(2, 2, 2, Ticket.Category.STANDARD));
  }

  @Test
  public void getTicketsByUserTest() {
    bookingService.bookTicket(333, 333, 333, Ticket.Category.STANDARD);
    User user = Mockito.mock(User.class);
    Mockito.when(user.getId()).thenReturn(333L);
    Assert.assertEquals(1, bookingService.getBookedTickets(user, 1, 1).size());
  }

  @Test
  public void getTicketsByEventTest() {
    bookingService.bookTicket(666, 666, 666, Ticket.Category.PREMIUM);
    Event event = Mockito.mock(Event.class);
    Mockito.when(event.getId()).thenReturn(666L);
    Assert.assertEquals(1, bookingService.getBookedTickets(event, 1, 1).size());
  }

  @Test
  public void cancelTicketTest() {
    Ticket ticket = bookingService.bookTicket(999, 999, 999, Ticket.Category.BAR);
    Assert.assertTrue(bookingService.cancelTicket(ticket.getId()));
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
