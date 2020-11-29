package dao;

import model.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class EventDaoTest {
  ClassPathXmlApplicationContext context;
  EventDao eventDao;

  public EventDaoTest() {}

  @Before
  public void setUp() {
    context = new ClassPathXmlApplicationContext("applicationContext.xml");
    eventDao = context.getBean(EventDao.class);
  }

  @Test
  public void createEventTest() {
    Event event = Mockito.mock(Event.class);
    Mockito.when(event.getId()).thenReturn(1L);
    Assert.assertEquals(event, eventDao.createEvent(event));
  }

  @Test
  public void createEventWithTheSameIdTest() {
    Event event1 = Mockito.mock(Event.class);
    Mockito.when(event1.getId()).thenReturn(1L);
    Event event2 = Mockito.mock(Event.class);
    Mockito.when(event2.getId()).thenReturn(1L);
    Assert.assertEquals(event1, eventDao.createEvent(event1));
    Assert.assertNull(eventDao.createEvent(event2));
  }

  @Test
  public void readEventByIdTest() {
    Event event1 = Mockito.mock(Event.class);
    Mockito.when(event1.getId()).thenReturn(1L);
    eventDao.createEvent(event1);
    Assert.assertEquals(event1, eventDao.readEventById(event1.getId()));
  }

  @Test
  public void readNotExistingEventByIdTest() {
    Event event1 = Mockito.mock(Event.class);
    Mockito.when(event1.getId()).thenReturn(1L);
    Assert.assertNull(eventDao.readEventById(event1.getId()));
  }

  @Test
  public void readEventsByTitleTest() {
    Event event1 = new EventImpl("Event 1", new Date(System.currentTimeMillis()));
    Event event2 = new EventImpl("Event 2", new Date(System.currentTimeMillis()));
    List<Event> events = Arrays.asList(event1, event2);
    eventDao.createEvent(event1);
    eventDao.createEvent(event2);
    Assert.assertEquals(events, eventDao.readEventsByTitle("Event"));
  }

  @Test
  public void readEventsByNotExistingTitleTest() {
    Event event1 = new EventImpl("Event 1", new Date(System.currentTimeMillis()));
    Event event2 = new EventImpl("Event 2", new Date(System.currentTimeMillis()));
    List<Event> events = new ArrayList<>();
    eventDao.createEvent(event1);
    eventDao.createEvent(event2);
    Assert.assertEquals(events, eventDao.readEventsByTitle("Disco"));
  }

  @Test
  public void readEventsForDayTest() {
    Event event1 = new EventImpl("Event 1", new Date(System.currentTimeMillis()));
    Event event2 = new EventImpl("Event 2", new Date(System.currentTimeMillis()));
    List<Event> events = Arrays.asList(event1, event2);
    eventDao.createEvent(event1);
    eventDao.createEvent(event2);
    Assert.assertEquals(events, eventDao.readEventsForDay(new Date(System.currentTimeMillis())));
  }

  @Test
  public void updateEventTest() {
    Event event1 = Mockito.mock(Event.class);
    Mockito.when(event1.getId()).thenReturn(1L);
    eventDao.createEvent(event1);
    Assert.assertEquals(event1, eventDao.updateEvent(event1));
  }

  @Test
  public void updateNotExistingEventTest() {
    Event event1 = Mockito.mock(Event.class);
    Mockito.when(event1.getId()).thenReturn(1L);
    Assert.assertNull(eventDao.updateEvent(event1));
  }

  @Test
  public void deleteEventTest() {
    Event event1 = Mockito.mock(Event.class);
    Mockito.when(event1.getId()).thenReturn(1L);
    eventDao.createEvent(event1);
    Assert.assertTrue(eventDao.deleteEvent(event1.getId()));
  }

  @Test
  public void deleteNotExistingEventTest() {
    Event event1 = Mockito.mock(Event.class);
    Mockito.when(event1.getId()).thenReturn(1L);
    Assert.assertFalse(eventDao.deleteEvent(event1.getId()));
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
