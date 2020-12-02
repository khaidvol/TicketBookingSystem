package dao;

import model.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class UserDao {

  private Log logger = LogFactory.getLog(UserDao.class);
  private BookingStorage bookingStorage;

  private UserDao() {}

  public User createUser(User user) {
    if (!bookingStorage.getUsers().containsKey(user.getId()) && isMailFree(user)) {
      bookingStorage.getUsers().put(user.getId(), user);
      logger.info("User created - ".concat(user.toString()));
      return bookingStorage.getUsers().get(user.getId());
    }
    logger.info("Failed to created user - ".concat(user.toString()));
    return null;
  }

  public User readUserById(long userId) {
    if (bookingStorage.getUsers().containsKey(userId)) {
      User user = bookingStorage.getUsers().get(userId);
      logger.info("User found: " + user.toString());
      return user;
    }
    logger.info("No users found by id: " + userId);
    return null;
  }

  public User readUserByEmail(String email) {
    for (User storedUser : bookingStorage.getUsers().values()) {
      if (storedUser.getEmail().equals(email)) {
        logger.info("User found: " + storedUser.toString());
        return storedUser;
      }
    }
    logger.info("No users found with email: " + email);
    return null;
  }

  public List<User> readUsersByName(String name) {
    List<User> users = new ArrayList<>();
    for (User storedUser : bookingStorage.getUsers().values()) {
      if (storedUser.getName().contains(name)) {
        users.add(storedUser);
      }
    }
    logger.info("Users found by name: " + name);
    users.forEach(logger::info);
    return users;
  }

  public User updateUser(User user) {
    if (bookingStorage.getUsers().containsKey(user.getId()) && isMailFree(user)) {
      bookingStorage.getUsers().replace(user.getId(), user);
      logger.info("User updated - " + user.toString());
      return bookingStorage.getUsers().get(user.getId());
    }
    logger.info("User not updated.");
    return null;
  }

  public boolean deleteUser(long userId) {
    if (bookingStorage.getUsers().containsKey(userId)) {
      bookingStorage.getUsers().remove(userId);
      logger.info("User deleted - id: " + userId);
      return true;
    }
    logger.info("User not deleted.");
    return false;
  }

  public boolean isMailFree(User user) {
    for (User storedUser : bookingStorage.getUsers().values()) {
      if (storedUser.getId() != user.getId() && storedUser.getEmail().equals(user.getEmail()))
        return false;
    }
    return true;
  }

  // used for setter-injection in xml config file.
  public void setBookingStorage(BookingStorage bookingStorage) {
    this.bookingStorage = bookingStorage;
  }
}
