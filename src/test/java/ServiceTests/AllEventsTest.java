package ServiceTests;

import DataAccessObjects.DataAccessException;
import DataAccessObjects.Database;
import Model.AuthToken;
import Model.Event;
import Model.Person;
import Model.User;
import Requests.AllEventsRequest;
import Results.AllEventsResult;
import Services.AllEventsService;
import Services.ClearService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AllEventsTest {

    Database db;
    AllEventsRequest request;
    AllEventsService service;
    Event[] posExpected;

    @BeforeEach
    public void setUp() throws Exception {
        try
        {
            ClearService clearService = new ClearService();
            clearService.service();

            db = new Database();
            db.openConnection();

            Event abbyBirth = new Event("abbyBirth", "abby", "abby123", 0, 0, "Russia", "Moscow", "Birth", 2000);
            Event allyBirth = new Event("allyBirth", "abby", "ally123", 11, 11, "France", "Paris", "Birth", 2001);
            Event bruceMarriage = new Event("bruceMarriage", "bobby", "bruce123", 33, 33, "Texas", "Paris", "Marriage", 2015);
            Event bobbyMarriage = new Event("bobbyMarriage", "bobby", "bobby123", 44, 44, "USA", "Frankfurt", "Marriage", 2000);
            Event bruceDeath = new Event("bruceDeath", "bobby", "bruce123", 66, 66, "USA", "NYC", "Death", 2072);
            Event bruceBirth = new Event("bruceBirth", "bobby", "bruce123", 77, 77, "USA", "LA", "Birth", 2000);
            Event bobbyBirth = new Event("bobbyBirth", "bobby", "bobby123", 22, 2, "Frankfurt", "Kentucky", "Birth", 1980);
            Event[] events = new Event[]{abbyBirth, allyBirth, bruceMarriage, bobbyMarriage, bruceDeath, bruceBirth, bobbyBirth};

            User aUser = new User("abby", "a", "a@email.com", "abby", "adams", "f", "abby123");
            User bUser = new User("bobby", "b", "b@email.com", "bobby", "bills", "m", "bobby123");
            User[] users = new User[]{aUser, bUser};

            Person abbyPerson = new Person("abby123", "abby", "abby", "adams", "f", null, null, null);
            Person bobbyPerson = new Person("personID2", "bobby", "bobby", "bills", "m", null, null, null);
            Person brucePerson = new Person("bruce123", "bobby", "bruce", "bills", "m", null, null, null);
            Person[] persons = new Person[]{abbyPerson, bobbyPerson, brucePerson};

            AuthToken abbyAuth = new AuthToken("12345","abby");
            AuthToken bobbyAuth = new AuthToken("67890","bobby");
            db.authDao.insert(abbyAuth);
            db.authDao.insert(bobbyAuth);

            for (int i = 0; i < events.length; i++) {
                db.eventDao.insert(events[i]);
            }
            for (int i = 0; i < users.length; i++) {
                db.userDao.insert(users[i]);
            }
            for (int i = 0; i < persons.length; i++) {
                db.personDao.insert(persons[i]);
            }
            request = new AllEventsRequest(abbyAuth);
            service = new AllEventsService();

            posExpected = new Event[] {abbyBirth, allyBirth};
            db.closeConnection(true);
        }
        catch(DataAccessException except)
        {
            System.out.println("Error in setup" + except.getMessage());
        }

    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        //Here we close the connection to the database file so it can be opened elsewhere.
        //We will leave commit to false because we have no need to save the changes to the database
        //between test cases
        ClearService clearService = new ClearService();
        clearService.service();
    }

    @Test
    public void posServiceTest() throws Exception {
        AllEventsResult result = service.service(request);
        assertEquals(null, result.getMessage());
        assertEquals(2, result.getData().length);
        assertArrayEquals(result.getData(), posExpected);
    }

    @Test
    public void negServiceTest() {
        request = new AllEventsRequest(new AuthToken("qwerty","abby"));
        AllEventsResult result = service.service(request);
        assertNotNull(result.getMessage());
        assertFalse(result.isSuccess());
    }
}
