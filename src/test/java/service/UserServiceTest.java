package service;

import model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import storage.BookingStorage;

public class UserServiceTest {

  ApplicationContext context;
  BookingStorage bookingStorage;
  UserService userService;
  User user;

  public UserServiceTest() {}

  @Before
  public void setUp() {
    context = new ClassPathXmlApplicationContext("applicationContextTest.xml");
    userService = context.getBean(UserService.class);
    bookingStorage = context.getBean("testingBookingStorage", BookingStorage.class);
    user = Mockito.mock(User.class);
  }

  @Test
  public void getUserByIdTest() {
    Assert.assertEquals(user, userService.createUser(user));
    Assert.assertEquals(user, userService.getUserById(user.getId()));
  }

  @Test(expected = IllegalStateException.class)
  public void getNotExistingUserByIdTest() {
    userService.getUserById(1000L);
  }

  @Test
  public void getUserByEmailTest() {
    Assert.assertNotNull(userService.getUserByEmail("jack@gmail.com"));
  }

  @Test(expected = IllegalStateException.class)
  public void getNotExistingUserByEmailTest() {
    userService.getUserByEmail("test@gmail.com");
  }

  @Test
  public void getUsersByNameTest() {
    Assert.assertNotNull(userService.getUsersByName("Jack", 1, 1));
  }

  @Test
  public void createUserTest() {
    Assert.assertEquals(user, userService.createUser(user));
  }

  @Test(expected = IllegalStateException.class)
  public void createUserWithUsedEmailTest() {
    Mockito.when(user.getEmail()).thenReturn("jack@gmail.com");
    userService.createUser(user);
  }

  @Test
  public void updateUserTest() {
    userService.createUser(user);
    Assert.assertEquals(user, userService.updateUser(user));
  }

  @Test(expected = IllegalStateException.class)
  public void updateNotExistingUserTest() {
    Mockito.when(user.getId()).thenReturn(100L);
    userService.updateUser(user);
  }

  @Test
  public void deleteUserTest() {
    Assert.assertTrue(userService.deleteUser(2));
  }

  @After
  public void cleanUp() {
    bookingStorage.cleanStorage();
  }
}
