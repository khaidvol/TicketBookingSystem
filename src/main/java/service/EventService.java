package service;

import dao.Dao;
import model.Event;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class EventService {

  private final Dao<Event> eventDao;

  private EventService(Dao<Event> eventDao) {
    this.eventDao = eventDao;
  }

  public Event getEventById(long eventId) {

    Event event = eventDao.read(eventId);

    if (event == null) throw new IllegalStateException();

    return event;
  }

  public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {

    List<Event> foundEvents = new ArrayList<>();

    for (Event event : eventDao.readAll()) {
      if (event.getTitle().contains(title)) {
        foundEvents.add(event);
      }
    }

    return foundEvents;
  }

  public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {

    List<Event> foundEvents = new ArrayList<>();

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(day);
    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

    for (Event event : eventDao.readAll()) {

      calendar.setTime(event.getDate());

      if (calendar.get(Calendar.DAY_OF_WEEK) == dayOfWeek) {
        foundEvents.add(event);
      }
    }

    return foundEvents;
  }

  public Event createEvent(Event event) {

    // set id for event (get current max id from storage)
    event.setId(eventDao.getMaxId() + 1);

    //create event
    eventDao.create(event);

    return eventDao.read(event.getId());
  }

  public Event updateEvent(Event event) {

    if (eventDao.read(event.getId()) == null) {
      throw new IllegalStateException();
    }

    eventDao.update(event);

    return eventDao.read(event.getId());
  }

  public boolean deleteEvent(long eventId) {

    boolean isEventDeleted = false;

    if (eventDao.read(eventId) != null) {
      eventDao.delete(eventId);
      isEventDeleted = true;
    }
    return isEventDeleted;
  }
}
