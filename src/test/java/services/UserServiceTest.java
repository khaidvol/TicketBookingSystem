package services;

import dao.BookingStorage;
import model.EventImpl;
import model.TicketImpl;
import model.User;
import model.UserImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.List;

public class UserServiceTest {

  ClassPathXmlApplicationContext context;
  UserService userService;

  public UserServiceTest() {}

  @Before
  public void setUp() {
    context = new ClassPathXmlApplicationContext("applicationContext.xml");
    userService = context.getBean(UserService.class);
  }

  @Test
  public void getUserByIdTest() {
    User user = Mockito.mock(User.class);
    userService.createUser(user);
    Assert.assertEquals(user, userService.getUserById(user.getId()));
  }

  @Test
  public void getUserByEmailTest() {
    User user = new UserImpl("Test", "test@gmail.com");
    userService.createUser(user);
    Assert.assertEquals(user, userService.getUserByEmail(user.getEmail()));
  }

  @Test
  public void getUsersByNameTest() {
    User user1 = new UserImpl("Test 1", "test1@gmail.com");
    User user2 = new UserImpl("Test 2", "test2@gmail.com");
    List<User> users = Arrays.asList(user1, user2);
    userService.createUser(user1);
    userService.createUser(user2);
    Assert.assertEquals(users, userService.getUsersByName("Test", 1, 1));
  }

  @Test
  public void createUserTest() {
    User user = Mockito.mock(User.class);
    Assert.assertEquals(user, userService.createUser(user));
  }

  @Test
  public void updatedUserTest() {
    User user = Mockito.mock(User.class);
    userService.createUser(user);
    Assert.assertEquals(user, userService.updateUser(user));
  }

  @Test
  public void deleteUserTest() {
    User user = Mockito.mock(User.class);
    userService.createUser(user);
    Assert.assertTrue(userService.deleteUser(user.getId()));
  }

  @After
  public void cleanUp() {
    BookingStorage bookingStorage = context.getBean(BookingStorage.class);
    bookingStorage.getUsers().clear();
    bookingStorage.getTickets().clear();
    bookingStorage.getEvents().clear();
    UserImpl.setIdCounter(1);
    EventImpl.setIdCounter(1);
    TicketImpl.setIdCounter(1);
  }
}
