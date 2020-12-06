package service;

import model.Event;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import storage.BookingStorage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventServiceTest {

  ApplicationContext context;
  BookingStorage bookingStorage;
  EventService eventService;
  Event event;

  public EventServiceTest() {}

  @Before
  public void setUp() {
    context = new ClassPathXmlApplicationContext("applicationContextTest.xml");
    eventService = context.getBean(EventService.class);
    bookingStorage = context.getBean("testingBookingStorage", BookingStorage.class);
    event = Mockito.mock(Event.class);
  }

  @Test
  public void getEventByIdTest() {
    Assert.assertEquals(event, eventService.createEvent(event));
    Assert.assertEquals(event, eventService.getEventById(event.getId()));
  }

  @Test(expected = IllegalStateException.class)
  public void getNotExistingEventByIdTest() {
    eventService.getEventById(1000L);
  }

  @Test
  public void getEventsByTitleTest() {
    Assert.assertNotNull(eventService.getEventsByTitle("Disco", 1, 1));
  }

  @Test
  public void getEventForDayTest() throws ParseException {
    String inputString = "2020-06-28";
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date inputDate = dateFormat.parse(inputString);
    Assert.assertNotNull(eventService.getEventsForDay(inputDate, 1, 1));
  }

  @Test
  public void createEventTest() {
    Assert.assertEquals(event, eventService.createEvent(event));
  }

  @Test
  public void updateEventTest() {
    Assert.assertEquals(event, eventService.createEvent(event));
    Assert.assertEquals(event, eventService.updateEvent(event));
  }

  @Test(expected = IllegalStateException.class)
  public void updateNotExistingEventTest() {
    Mockito.when(event.getId()).thenReturn(100L);
    eventService.updateEvent(event);
  }

  @Test
  public void deleteEventTest() {
    Assert.assertEquals(event, eventService.createEvent(event));
    Assert.assertTrue(eventService.deleteEvent(event.getId()));
  }

  @After
  public void cleanUp() {
    bookingStorage.cleanStorage();
  }
}
