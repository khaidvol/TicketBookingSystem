package service;

import storage.BookingStorage;
import model.Event;
import model.implementation.EventImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class EventServiceTest {

  ClassPathXmlApplicationContext context;
  EventService eventService;

  public EventServiceTest() {}

  @Before
  public void setUp() {
    context = new ClassPathXmlApplicationContext("applicationContext.xml");
    eventService = context.getBean(EventService.class);
  }

  @Test
  public void getEventByIdTest() {
    Event event = Mockito.mock(Event.class);
    eventService.createEvent(event);
    Assert.assertEquals(event, eventService.getEventById(event.getId()));
  }

  @Test
  public void getEventsByTitleTest() {
    Event event1 = new EventImpl("Event 1", new Date(System.currentTimeMillis()));
    Event event2 = new EventImpl("Event 2", new Date(System.currentTimeMillis()));
    List<Event> events = Arrays.asList(event1, event2);
    eventService.createEvent(event1);
    eventService.createEvent(event2);
    Assert.assertEquals(events, eventService.getEventsByTitle("Event", 1, 1));
  }

  @Test
  public void getEventForDayTest() {
    Event event1 = new EventImpl("Event 1", new Date(System.currentTimeMillis()));
    Event event2 = new EventImpl("Event 2", new Date(System.currentTimeMillis()));
    List<Event> events = Arrays.asList(event1, event2);
    eventService.createEvent(event1);
    eventService.createEvent(event2);
    Assert.assertEquals(
        events, eventService.getEventsForDay(new Date(System.currentTimeMillis()), 1, 1));
  }

  @Test
  public void createEventTest() {
    Event event = Mockito.mock(Event.class);
    Assert.assertEquals(event, eventService.createEvent(event));
  }

  @Test
  public void updatedEventTest() {
    Event event = Mockito.mock(Event.class);
    eventService.createEvent(event);
    Assert.assertEquals(event, eventService.updateEvent(event));
  }

  @Test
  public void deleteEventTest() {
    Event event = Mockito.mock(Event.class);
    eventService.createEvent(event);
    Assert.assertTrue(eventService.deleteEvent(event.getId()));
  }

  @After
  public void cleanUp() {
    BookingStorage bookingStorage = context.getBean(BookingStorage.class);
    bookingStorage.getUsers().clear();
    bookingStorage.getTickets().clear();
    bookingStorage.getEvents().clear();
  }
}
