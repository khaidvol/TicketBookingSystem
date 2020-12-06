package service;

import dao.Dao;
import model.Event;
import model.Ticket;
import model.User;
import model.implementation.TicketImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import storage.BookingStorage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class TicketService {

  private static final Log LOGGER = LogFactory.getLog(TicketService.class);
  private final Dao<Ticket> ticketDao;
  private final BookingStorage bookingStorage;

  // constructor-injection
  private TicketService(Dao<Ticket> ticketDao, BookingStorage bookingStorage) {
    this.ticketDao = ticketDao;
    this.bookingStorage = bookingStorage;
  }

  public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {

    Ticket ticket = new TicketImpl(userId, eventId, place, category);
    ticket.setId(ticketDao.getMaxId() + 1);

    if (!isPlaceFree(ticket)) {
      LOGGER.error(
          String.format("Ticket booking failed. Place %s is already booked.", ticket.getPlace()));
      throw new IllegalStateException();
    }

    ticketDao.create(ticket);
    LOGGER.info("Ticket booked successfully. Ticket details: " + ticket.toString());

    return ticketDao.read(ticket.getId());
  }

  public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {

    List<Ticket> tickets = new ArrayList<>();

    for (Ticket ticket : ticketDao.readAll()) {
      if (ticket.getUserId() == user.getId()) {
        tickets.add(ticket);
      }
    }

    tickets.sort(
        (t1, t2) ->
            bookingStorage
                .getEvents()
                .get(t2.getEventId())
                .getDate()
                .compareTo(bookingStorage.getEvents().get(t1.getEventId()).getDate()));

    LOGGER.info(String.format("%s ticket(s) found: ", tickets.size()));
    tickets.forEach(LOGGER::info);

    return tickets;
  }

  public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {

    List<Ticket> tickets = new ArrayList<>();

    for (Ticket ticket : ticketDao.readAll()) {
      if (ticket.getEventId() == event.getId()) {
        tickets.add(ticket);
      }
    }
    tickets.sort(
        Comparator.comparing(t -> bookingStorage.getUsers().get(t.getUserId()).getEmail()));

    LOGGER.info(String.format("%s ticket(s) found: ", tickets.size()));
    tickets.forEach(LOGGER::info);

    return tickets;
  }

  public boolean cancelTicket(long ticketId) {

    boolean isTicketCanceled = false;

    if (ticketDao.read(ticketId) != null) {
      ticketDao.delete(ticketId);
      isTicketCanceled = true;
    }

    LOGGER.info("Ticket canceled: " + isTicketCanceled);

    return isTicketCanceled;
  }

  // private method for place check
  private boolean isPlaceFree(Ticket ticket) {

    boolean isPlaceFree = true;

    for (Ticket storedTicket : ticketDao.readAll()) {
      if (storedTicket.getEventId() == ticket.getEventId()
          && storedTicket.getPlace() == ticket.getPlace()) {
        isPlaceFree = false;
      }
    }

    return isPlaceFree;
  }
}
