package dao;

import model.Event;
import model.Ticket;
import model.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TicketDao {

  private Log logger = LogFactory.getLog(TicketDao.class);
  private BookingStorage bookingStorage;

  private TicketDao() {}

  public Ticket createTicket(Ticket ticket) {
    if (!isPlaceFree(ticket)) throw new IllegalStateException();
    if (!bookingStorage.getTickets().containsKey(ticket.getId())) {
      bookingStorage.getTickets().put(ticket.getId(), ticket);
      logger.info("Ticket created: " + ticket.toString());
      return ticket;
    }
    logger.info("Failed to create ticket.");
    return null;
  }

  public List<Ticket> readTicketsByUser(User user) {
    List<Ticket> tickets = new ArrayList<>();
    for (Ticket ticket : bookingStorage.getTickets().values()) {
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
    logger.info("Tickets found for user id: " + user.getId());
    tickets.forEach(logger::info);
    return tickets;
  }

  public List<Ticket> readTicketsByEvent(Event event) {
    List<Ticket> tickets = new ArrayList<>();
    for (Ticket ticket : bookingStorage.getTickets().values()) {
      if (ticket.getEventId() == event.getId()) {
        tickets.add(ticket);
      }
    }
    tickets.sort(
        Comparator.comparing(t -> bookingStorage.getUsers().get(t.getUserId()).getEmail()));
    logger.info("Tickets found for event id: " + event.getId());
    tickets.forEach(logger::info);
    return tickets;
  }

  public boolean deleteTicket(long ticketId) {
    if (bookingStorage.getTickets().containsKey(ticketId)) {
      bookingStorage.getTickets().remove(ticketId);
      logger.info("Ticket deleted - id: " + ticketId);
      return true;
    }
    logger.info("Ticket not deleted.");
    return false;
  }

  public boolean isPlaceFree(Ticket ticket) {
    for (Ticket storedTicket : bookingStorage.getTickets().values()) {
      if (storedTicket.getEventId() == ticket.getEventId()
          && storedTicket.getPlace() == ticket.getPlace()) return false;
    }
    return true;
  }

  // used for setter-injection in xml config file.
  public void setBookingStorage(BookingStorage bookingStorage) {
    this.bookingStorage = bookingStorage;
  }
}
