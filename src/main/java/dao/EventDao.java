package dao;

import model.Event;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EventDao {

  private Log logger = LogFactory.getLog(EventDao.class);
  private BookingStorage bookingStorage;

  private EventDao() {}

  public Event createEvent(Event event) {
    if (!bookingStorage.getEvents().containsKey(event.getId())) {
      bookingStorage.getEvents().put(event.getId(), event);
      logger.info("Event created - ".concat(event.toString()));
      return bookingStorage.getEvents().get(event.getId());
    }
    logger.info("Failed to created event - ".concat(event.toString()));
    return null;
  }

  public Event readEventById(long eventId) {
    if (bookingStorage.getEvents().containsKey(eventId)) {
      Event event = bookingStorage.getEvents().get(eventId);
      logger.info("Event found: " + event.toString());
      return event;
    }
    logger.info("No events found by id: " + eventId);
    return null;
  }

  public List<Event> readEventsByTitle(String title) {
    List<Event> events = new ArrayList<>();
    for (Event storedEvent : bookingStorage.getEvents().values()) {
      if (storedEvent.getTitle().contains(title)) events.add(storedEvent);
    }
    logger.info("Events found by title: " + title);
    events.forEach(logger::info);
    return events;
  }

  public List<Event> readEventsForDay(Date day) {
    List<Event> events = new ArrayList<>();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(day);
    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
    for (Event storedEvent : bookingStorage.getEvents().values()) {
      calendar.setTime(storedEvent.getDate());
      if (calendar.get(Calendar.DAY_OF_WEEK) == dayOfWeek) events.add(storedEvent);
    }
    logger.info("Events found for requested day: ");
    events.forEach(logger::info);
    return events;
  }

  public Event updateEvent(Event event) {
    if (bookingStorage.getEvents().containsKey(event.getId())) {
      bookingStorage.getEvents().replace(event.getId(), event);
      logger.info("Event updated - " + event.toString());
      return bookingStorage.getEvents().get(event.getId());
    }
    logger.info("Event not updated.");
    return null;
  }

  public boolean deleteEvent(long eventId) {
    if (bookingStorage.getEvents().containsKey(eventId)) {
      bookingStorage.getEvents().remove(eventId);
      logger.info("Event deleted - id: " + eventId);
      return true;
    }
    logger.info("Event not deleted.");
    return false;
  }

  // used for setter-injection in xml config file.
  public void setBookingStorage(BookingStorage bookingStorage) {
    this.bookingStorage = bookingStorage;
  }
}
