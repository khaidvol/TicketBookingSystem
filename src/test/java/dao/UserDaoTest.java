package dao;

import dao.implementation.UserDao;
import model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import storage.BookingStorage;

public class UserDaoTest {

  ApplicationContext context;
  BookingStorage bookingStorage;
  Dao<User> userDao;
  User user;

  public UserDaoTest() {}

  @Before
  public void setUp() {

    context = new ClassPathXmlApplicationContext("applicationContextTest.xml");
    bookingStorage = context.getBean("testingBookingStorage", BookingStorage.class);
    userDao = context.getBean("testUserDao", UserDao.class);

    user = Mockito.mock(User.class);
    Mockito.when(user.getId()).thenReturn(10L);
  }

  @Test
  public void createTest() {
    Assert.assertNull(userDao.create(user));
    Assert.assertEquals(user, userDao.read(user.getId()));
  }

  @Test
  public void readTest() {
    Assert.assertNull(userDao.create(user));
    Assert.assertEquals(user, userDao.read(user.getId()));
  }

  @Test
  public void readAllTest() {
    Assert.assertNull(userDao.create(user));
    Assert.assertNotNull(userDao.readAll());
  }

  @Test
  public void updateTest() {
    Assert.assertNull(userDao.create(user));
    Assert.assertEquals(user, userDao.update(user));
  }

  @Test
  public void deleteTest() {
    Assert.assertNull(userDao.create(user));
    Assert.assertEquals(user, userDao.delete(user.getId()));
  }

  @Test
  public void getMaxIdWhenStorageNotEmptyTest() {
    Assert.assertNull(userDao.create(user));
    Assert.assertNotNull(userDao.getMaxId());
  }

  @Test
  public void getMaxIdWhenStorageIsEmptyTest() {
    bookingStorage.getUsers().clear();
    Assert.assertNotNull(userDao.getMaxId());
  }

  @After
  public void cleanUp() {
    bookingStorage.cleanStorage();
  }
}
