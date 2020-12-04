package dao.implementation;

import storage.BookingStorage;
import dao.Dao;
import model.Ticket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicketDao implements Dao<Ticket> {

  private BookingStorage bookingStorage;

  private TicketDao() {}

  @Override
  public Ticket create(Ticket ticket) {
    return bookingStorage.getTickets().put(ticket.getId(), ticket);
  }

  @Override
  public Ticket read(long ticketId) {
    return bookingStorage.getTickets().get(ticketId);
  }

  @Override
  public List<Ticket> readAll() {
    return new ArrayList<>(bookingStorage.getTickets().values());
  }

  @Override
  public Ticket update(Ticket ticket) {
    return bookingStorage.getTickets().replace(ticket.getId(), ticket);
  }

  @Override
  public Ticket delete(long ticketId) {
    return bookingStorage.getTickets().remove(ticketId);
  }

  @Override
  public Long getMaxId() {
    return bookingStorage.getTickets().keySet().isEmpty()
        ? 0L
        : Collections.max(bookingStorage.getTickets().keySet());
  }

  // used for setter-injection in xml config file.
  public void setBookingStorage(BookingStorage bookingStorage) {
    this.bookingStorage = bookingStorage;
  }
}
