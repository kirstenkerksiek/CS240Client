package DaoTests;

import DataAccessObjects.DataAccessException;
import DataAccessObjects.Database;
import DataAccessObjects.EventDao;
import Model.Event;
import Model.Person;
import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

//We will use this to test that our insert method is working and failing in the right ways
public class EventDaoTest {
    private Database db;
    private Event bestEvent;
    private EventDao eDao;

    @BeforeEach
    public void setUp() throws DataAccessException
    {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new event with random data
        bestEvent = new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);
        //Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Let's clear the database as well so any lingering data doesn't affect our tests
        db.clearTables();
        //Then we pass that connection to the EventDAO so it can access the database
        eDao = new EventDao(conn);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        //Here we close the connection to the database file so it can be opened elsewhere.
        //We will leave commit to false because we have no need to save the changes to the database
        //between test cases
        db.closeConnection(false);
    }

    @Test
    public void findEventsPass() throws DataAccessException {
        Event e1 = new Event("abc", "kirsten", "123", 20, 30, "USA", "Denver", "Birth", 2002);
        Event e2 = new Event("def", "cruz", "123", 78, 20, "England", "London", "Marriage", 2025);
        Event e3 = new Event("ghi", "katanya", "123", 100, 50, "Australia", "Sydney", "Death", 2080);
        Event e4 = new Event("xyz", "kirsten", "321", 100, 50, "Australia", "Sydney", "Birth", 2030);
        Event e5 = new Event("uvw", "kirsten", "789", 56, 10, "Japan", "Tokya", "Death", 2100);
        eDao.insert(e1);
        eDao.insert(e2);
        eDao.insert(e3);
        eDao.insert(e4);
        eDao.insert(e5);
        Event[] actual = eDao.findEvents("kirsten");
        assertEquals(actual.length, 3);
        Event found1 = e1;
        Event found2 = e4;
        Event found3 = e5;
        assertEquals(found1,actual[0]);
        assertEquals(found2,actual[1]);
        assertEquals(found3,actual[2]);
    }

    @Test
    public void findEventsFail() throws DataAccessException {
        Event e1 = new Event("abc", "kirsten", "123", 20, 30, "USA", "Denver", "Birth", 2002);
        Event e2 = new Event("def", "kirsten", "123", 78, 20, "England", "London", "Marriage", 2025);
        Event e3 = new Event("ghi", "kirsten", "123", 100, 50, "Australia", "Sydney", "Death", 2080);
        Event e4 = new Event("xyz", "kirsten", "321", 100, 50, "Australia", "Sydney", "Birth", 2030);
        Event e5 = new Event("uvw", "kirsten", "789", 56, 10, "Japan", "Tokya", "Death", 2100);
        eDao.insert(e1);
        eDao.insert(e2);
        eDao.insert(e3);
        eDao.insert(e4);
        eDao.insert(e5);
        Event[] actual = eDao.findEvents("Australia");
        assertNull(actual);
        assertThrows(NullPointerException.class, ()-> eDao.findEvents(null));
    }

    @Test
    public void getEventsPass() throws DataAccessException {
        Event e1 = new Event("abc", "kirsten", "123", 20, 30, "USA", "Denver", "Birth", 2002);
        Event e2 = new Event("def", "kirsten", "123", 78, 20, "England", "London", "Marriage", 2025);
        Event e3 = new Event("ghi", "kirsten", "123", 100, 50, "Australia", "Sydney", "Death", 2080);
        Event e4 = new Event("xyz", "kirsten", "321", 100, 50, "Australia", "Sydney", "Birth", 2030);
        Event e5 = new Event("uvw", "kirsten", "789", 56, 10, "Japan", "Tokya", "Death", 2100);
        eDao.insert(e1);
        eDao.insert(e2);
        eDao.insert(e3);
        eDao.insert(e4);
        eDao.insert(e5);
        Event[] actual = eDao.getEventsByPerson("123");
        assertEquals(actual.length, 3);
        Event[] expected = new Event[]{e1, e2, e3};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void getEventsFail() throws DataAccessException {
        Event e1 = new Event("abc", "kirsten", "123", 20, 30, "USA", "Denver", "Birth", 2002);
        Event e2 = new Event("def", "kirsten", "123", 78, 20, "England", "London", "Marriage", 2025);
        Event e3 = new Event("ghi", "kirsten", "123", 100, 50, "Australia", "Sydney", "Death", 2080);
        Event e4 = new Event("xyz", "kirsten", "321", 100, 50, "Australia", "Sydney", "Birth", 2030);
        Event e5 = new Event("uvw", "kirsten", "789", 56, 10, "Japan", "Tokya", "Death", 2100);
        eDao.insert(e1);
        eDao.insert(e2);
        eDao.insert(e3);
        eDao.insert(e4);
        eDao.insert(e5);
        Event[] actual = eDao.getEventsByPerson("abc");
        assertNull(actual);
        assertThrows(NullPointerException.class, ()-> eDao.getEventsByPerson(null), "importing a null string didn't throw an exception.");
    }


    @Test
    public void deleteEventsPass() throws DataAccessException {
        Event e1 = new Event("abc", "kirsten", "123", 20, 30, "USA", "Denver", "Birth", 2002);
        Event e2 = new Event("def", "kirsten", "123", 78, 20, "England", "London", "Marriage", 2025);
        Event e3 = new Event("ghi", "sky", "321", 100, 50, "Australia", "Sydney", "Death", 2080);
        Event e4 = new Event("xyz", "skye", "321", 100, 50, "Australia", "Sydney", "Birth", 2030);
        Event e5 = new Event("uvw", "skye", "321", 56, 10, "Japan", "Tokya", "Death", 2100);
        eDao.insert(e1);
        eDao.insert(e2);
        eDao.insert(e3);
        eDao.insert(e4);
        eDao.insert(e5);
        eDao.deleteEventsByUsername("skye");
        Event[] events = eDao.getEventsByPerson("321");
        assertEquals(events.length, 1);
        Event found1 = e3;
        assertEquals(found1, events[0]);
    }

    @Test
    public void deleteEventsFail() throws DataAccessException {
        Event e1 = new Event("abc", "kirsten", "123", 20, 30, "USA", "Denver", "Birth", 2002);
        Event e2 = new Event("def", "kirsten", "123", 78, 20, "England", "London", "Marriage", 2025);
        Event e3 = new Event("ghi", "kirsten", "123", 100, 50, "Australia", "Sydney", "Death", 2080);
        Event e4 = new Event("xyz", "skye", "321", 100, 50, "Australia", "Sydney", "Birth", 2030);
        Event e5 = new Event("uvw", "skye", "321", 56, 10, "Japan", "Tokya", "Death", 2100);
        eDao.insert(e1);
        eDao.insert(e2);
        eDao.insert(e3);
        eDao.insert(e4);
        eDao.insert(e5);
        eDao.deleteEventsByUsername("kirsten");
        assertNull(eDao.findEvents("kirsten"));
        assertThrows(NullPointerException.class, ()-> eDao.deleteEventsByUsername(null), "importing a null string didn't throw an exception.");
    }

    @Test
    public void insertPass() throws DataAccessException {
        //While insert returns a bool we can't use that to verify that our function actually worked
        //only that it ran without causing an error
        eDao.insert(bestEvent);
        //So lets use a find method to get the event that we just put in back out
        Event compareTest = eDao.find(bestEvent.getEventID());
        //First lets see if our find found anything at all. If it did then we know that if nothing
        //else something was put into our database, since we cleared it in the beginning
        assertNotNull(compareTest);
        //Now lets make sure that what we put in is exactly the same as what we got out. If this
        //passes then we know that our insert did put something in, and that it didn't change the
        //data in any way
        assertEquals(bestEvent, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        //lets do this test again but this time lets try to make it fail
        //if we call the method the first time it will insert it successfully
        eDao.insert(bestEvent);
        //but our sql table is set up so that "eventID" must be unique. So trying to insert it
        //again will cause the method to throw an exception
        //Note: This call uses a lambda function. What a lambda function is is beyond the scope
        //of this class. All you need to know is that this line of code runs the code that
        //comes after the "()->" and expects it to throw an instance of the class in the first parameter.
        assertThrows(DataAccessException.class, ()-> eDao.insert(bestEvent));
    }

    @Test
    public void posRetrievalUser() throws DataAccessException {
        eDao.insert(bestEvent);
        Event foundEvent = eDao.find(bestEvent.getEventID());
        assertEquals(foundEvent, bestEvent);
    }

    @Test
    public void negRetrievalUser() throws DataAccessException {
        eDao.insert(bestEvent);
        Event foundEvent = eDao.find("abcdef");
        assertNull(foundEvent);
        assertThrows(NullPointerException.class, ()-> eDao.find(null), "importing a null string didn't throw an exception.");
    }

    @Test
    public void posClear() throws DataAccessException {
        eDao.insert(bestEvent);
        db.clearTables();
        assertNull(eDao.find(bestEvent.getEventID()));
    }
}


