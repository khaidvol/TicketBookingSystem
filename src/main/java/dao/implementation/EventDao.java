package dao.implementation;

import storage.BookingStorage;
import dao.Dao;
import model.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventDao implements Dao<Event> {

  private BookingStorage bookingStorage;

  private EventDao() {}

  @Override
  public Event create(Event event) {
    return bookingStorage.getEvents().put(event.getId(), event);
  }

  @Override
  public Event read(long eventId) {
    return bookingStorage.getEvents().get(eventId);
  }

  @Override
  public List<Event> readAll() {
    return new ArrayList<>(bookingStorage.getEvents().values());
  }

  @Override
  public Event update(Event event) {
    return bookingStorage.getEvents().replace(event.getId(), event);
  }

  @Override
  public Event delete(long eventId) {
    return bookingStorage.getEvents().remove(eventId);
  }

  @Override
  public Long getMaxId() {
    return bookingStorage.getEvents().keySet().isEmpty()
        ? 0L
        : Collections.max(bookingStorage.getEvents().keySet());
  }

  // used for setter-injection in xml config file.
  public void setBookingStorage(BookingStorage bookingStorage) {
    this.bookingStorage = bookingStorage;
  }
}
