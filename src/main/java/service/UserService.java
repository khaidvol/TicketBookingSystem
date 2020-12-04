package service;

import dao.Dao;
import model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

  private final Dao<User> userDao;

  private UserService(Dao<User> userDao) {
    this.userDao = userDao;
  }

  public User getUserById(long userId) {

    User user = userDao.read(userId);

    if (user == null) throw new IllegalStateException();

    return user;
  }

  public User getUserByEmail(String email) {

    for (User user : userDao.readAll()) {
      if (user.getEmail().equals(email)) {
        return user;
      }
    }

    throw new IllegalStateException();
  }

  public List<User> getUsersByName(String name, int pageSize, int pageNum) {

    List<User> foundUsers = new ArrayList<>();

    for (User user : userDao.readAll()) {
      if (user.getName().contains(name)) {
        foundUsers.add(user);
      }
    }
    return foundUsers;
  }

  public User createUser(User user) {

    // set id for user (get current max id from storage)
    user.setId(userDao.getMaxId() + 1);

    // check if email is free
    if (!isMailFree(user)) throw new IllegalStateException();

    // create user
    userDao.create(user);

    return userDao.read(user.getId());
  }

  public User updateUser(User user) {

    if (userDao.read(user.getId()) == null || !isMailFree(user)) {
      throw new IllegalStateException();
    }

    userDao.update(user);

    return userDao.read(user.getId());
  }

  public boolean deleteUser(long userId) {

    boolean isUserDeleted = false;

    if (userDao.read(userId) != null) {
      userDao.delete(userId);
      isUserDeleted = true;
    }
    return isUserDeleted;
  }

  // private method for mail check
  private boolean isMailFree(User user) {

    boolean isMailFree = true;

    for (User storedUser : userDao.readAll()) {
      if (storedUser.getId() != user.getId() && storedUser.getEmail().equals(user.getEmail()))
        isMailFree = false;
    }
    return isMailFree;
  }
}
