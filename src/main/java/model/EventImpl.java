package model;

import java.util.Date;

public class EventImpl implements Event {
  private static long idCounter = 1;
  private long id;
  private String title;
  private Date date;

  public EventImpl() {}

  // constructor with autogenerated id for creating event
  public EventImpl(String title, Date date) {
    this.id = idCounter++;
    this.title = title;
    this.date = date;
  }

  @Override
  public long getId() {
    return id;
  }

  @Override
  public void setId(long id) {
    this.id = id;
  }

  @Override
  public String getTitle() {
    return title;
  }

  @Override
  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public Date getDate() {
    return date;
  }

  @Override
  public void setDate(Date date) {
    this.date = date;
  }

  public static void setIdCounter(long idCounter) {
    EventImpl.idCounter = idCounter;
  }

  @Override
  public String toString() {
    return "Event: " + "id=" + id + ", title='" + title + '\'' + ", date=" + date + '.';
  }
}
