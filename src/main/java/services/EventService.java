package services;

import dao.EventDao;
import model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EventService {

  @Autowired private EventDao eventDao;

  private EventService() {}

  public Event createEvent(Event event) {
    return eventDao.createEvent(event);
  }

  public Event getEventById(long eventId) {
    return eventDao.readEventById(eventId);
  }

  public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
    return eventDao.readEventsByTitle(title);
  }

  public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
    return eventDao.readEventsForDay(day);
  }

  public Event updateEvent(Event event) {
    return eventDao.updateEvent(event);
  }

  public boolean deleteEvent(long eventId) {
    return eventDao.deleteEvent(eventId);
  }
}
