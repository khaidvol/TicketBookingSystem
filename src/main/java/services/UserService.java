package services;

import dao.UserDao;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

  @Autowired private UserDao userDao;

  private UserService() {}

  public User getUserById(long userId) {
    return userDao.readUserById(userId);
  }

  public User getUserByEmail(String email) {
    return userDao.readUserByEmail(email);
  }

  public List<User> getUsersByName(String name, int pageSize, int pageNum) {
    return userDao.readUsersByName(name);
  }

  public User createUser(User user) {
    return userDao.createUser(user);
  }

  public User updateUser(User user) {
    return userDao.updateUser(user);
  }

  public boolean deleteUser(long userId) {
    return userDao.deleteUser(userId);
  }
}
