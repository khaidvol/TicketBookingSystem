package dao;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDaoTest {

  ClassPathXmlApplicationContext context;
  UserDao userDao;

  public UserDaoTest() {}

  @Before
  public void setUp() {
    context = new ClassPathXmlApplicationContext("applicationContext.xml");
    userDao = context.getBean(UserDao.class);
  }

  @Test
  public void createUserTest() {
    User user = Mockito.mock(User.class);
    Mockito.when(user.getId()).thenReturn(1L);
    Assert.assertEquals(user, userDao.createUser(user));
  }

  @Test
  public void createUserWithTheSameIdTest() {
    User user1 = Mockito.mock(User.class);
    Mockito.when(user1.getId()).thenReturn(1L);
    User user2 = Mockito.mock(User.class);
    Mockito.when(user2.getId()).thenReturn(1L);
    Assert.assertEquals(user1, userDao.createUser(user1));
    Assert.assertNull(userDao.createUser(user2));
  }

  @Test
  public void createUserWithTheSameEmailTest() {
    User user1 = Mockito.mock(User.class);
    Mockito.when(user1.getEmail()).thenReturn("test@gmail.com");
    User user2 = Mockito.mock(User.class);
    Mockito.when(user2.getEmail()).thenReturn("test@gmail.com");
    Assert.assertEquals(user1, userDao.createUser(user1));
    Assert.assertNull(userDao.createUser(user2));
  }

  @Test
  public void readUserByIdTest() {
    User user1 = Mockito.mock(User.class);
    Mockito.when(user1.getId()).thenReturn(1L);
    userDao.createUser(user1);
    Assert.assertEquals(user1, userDao.readUserById(user1.getId()));
  }

  @Test
  public void readNotExistingUserByIdTest() {
    User user1 = Mockito.mock(User.class);
    Mockito.when(user1.getId()).thenReturn(1L);
    Assert.assertNull(userDao.readUserById(user1.getId()));
  }

  @Test
  public void readUserByEmailTest() {
    User user1 = Mockito.mock(User.class);
    Mockito.when(user1.getEmail()).thenReturn("test@gmail");
    userDao.createUser(user1);
    Assert.assertEquals(user1, userDao.readUserByEmail(user1.getEmail()));
  }

  @Test
  public void readNotExistingUserByEmailTest() {
    User user1 = new UserImpl("Tester 1", "test1@gmail.com");
    userDao.createUser(user1);
    Assert.assertNull(userDao.readUserByEmail("test@gmail.com"));
  }

  @Test
  public void readUsersByNameTest() {
    User user1 = new UserImpl("Tester 1", "test1@gmail.com");
    User user2 = new UserImpl("Tester 2", "test2@gmail.com");
    List<User> users = Arrays.asList(user1, user2);
    userDao.createUser(user1);
    userDao.createUser(user2);
    Assert.assertEquals(users, userDao.readUsersByName("Tester"));
  }

  @Test
  public void readUsersByNotExistingNameTest() {
    User user1 = new UserImpl("Tester 1", "test1@gmail.com");
    User user2 = new UserImpl("Tester 2", "test2@gmail.com");
    List<User> users = new ArrayList<>();
    userDao.createUser(user1);
    userDao.createUser(user2);
    Assert.assertEquals(users, userDao.readUsersByName("User"));
  }

  @Test
  public void updateUserTest() {
    User user1 = Mockito.mock(User.class);
    Mockito.when(user1.getId()).thenReturn(1L);
    userDao.createUser(user1);
    Assert.assertEquals(user1, userDao.updateUser(user1));
  }

  @Test
  public void updateNotExistingUserTest() {
    User user1 = Mockito.mock(User.class);
    Mockito.when(user1.getId()).thenReturn(1L);
    Assert.assertNull(userDao.updateUser(user1));
  }

  @Test
  public void updateUserWithExistingEmailTest() {
    User user1 = new UserImpl("Tester1", "test1@gmail.com");
    User user2 = new UserImpl("Tester2", "test2@gmail.com");
    userDao.createUser(user1);
    userDao.createUser(user2);
    user1.setEmail("test2@gmail.com");
    Assert.assertNull(userDao.updateUser(user1));
  }

  @Test
  public void deleteUserTest() {
    User user1 = Mockito.mock(User.class);
    Mockito.when(user1.getId()).thenReturn(1L);
    userDao.createUser(user1);
    Assert.assertTrue(userDao.deleteUser(user1.getId()));
  }

  @Test
  public void deleteNotExistingUserTest() {
    User user1 = Mockito.mock(User.class);
    Mockito.when(user1.getId()).thenReturn(1L);
    userDao.createUser(user1);
    User user2 = Mockito.mock(User.class);
    Mockito.when(user2.getId()).thenReturn(2L);
    Assert.assertFalse(userDao.deleteUser(user2.getId()));
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
