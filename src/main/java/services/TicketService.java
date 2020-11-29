package services;

import dao.TicketDao;
import model.Event;
import model.Ticket;
import model.TicketImpl;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

  @Autowired private TicketDao ticketDao;

  private TicketService() {}

  public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
    return ticketDao.createTicket(new TicketImpl(userId, eventId, place, category));
  }

  public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
    return ticketDao.readTicketsByUser(user);
  }

  public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
    return ticketDao.readTicketsByEvent(event);
  }

  public boolean cancelTicket(long ticketId) {
    return ticketDao.deleteTicket(ticketId);
  }
}
