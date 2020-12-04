import dao.EventDaoTest;
import dao.TicketDaoTest;
import dao.UserDaoTest;
import integration.IntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import service.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  UserDaoTest.class,
  EventDaoTest.class,
  TicketDaoTest.class,
  UserServiceTest.class,
  EventServiceTest.class,
  TicketServiceTest.class,
  BookingFacadeImplTest.class,
  IntegrationTest.class,
})
public class TestAll {}
