package DaoTests;

import DataAccessObjects.DataAccessException;
import DataAccessObjects.Database;
import DataAccessObjects.UserDao;
import Model.Person;
import Model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import DataAccessObjects.EventDao;
import Model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

//We will use this to test that our insert method is working and failing in the right ways
public class UserDaoTest {
    private Database db;
    private User bestUser;
    private UserDao uDao;

    @BeforeEach
    public void setUp() throws DataAccessException
    {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        db.openConnection();
        //and a new event with random data
        bestUser = new User("user1", "user123", "user@users.com", "User", "Smith", "m", "10000001");
        //Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Let's clear the database as well so any lingering data doesn't affect our tests
        db.clearTables();
        //Then we pass that connection to the EventDAO so it can access the database
        uDao = new UserDao(conn);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        //Here we close the connection to the database file so it can be opened elsewhere.
        //We will leave commit to false because we have no need to save the changes to the database
        //between test cases
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        //While insert returns a bool we can't use that to verify that our function actually worked
        //only that it ran without causing an error
        uDao.insert(bestUser);
        //So lets use a find method to get the event that we just put in back out
        User compareTest = uDao.find(bestUser.getUsername());
        //First lets see if our find found anything at all. If it did then we know that if nothing
        //else something was put into our database, since we cleared it in the beginning
        assertNotNull(compareTest);
        //Now lets make sure that what we put in is exactly the same as what we got out. If this
        //passes then we know that our insert did put something in, and that it didn't change the
        //data in any way
        assertEquals(bestUser, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        //lets do this test again but this time lets try to make it fail
        //if we call the method the first time it will insert it successfully
        uDao.insert(bestUser);
        //but our sql table is set up so that "eventID" must be unique. So trying to insert it
        //again will cause the method to throw an exception
        //Note: This call uses a lambda function. What a lambda function is is beyond the scope
        //of this class. All you need to know is that this line of code runs the code that
        //comes after the "()->" and expects it to throw an instance of the class in the first parameter.
        assertThrows(DataAccessException.class, ()-> uDao.insert(bestUser));
    }

    @Test
    public void posRetrievalUser() throws DataAccessException {
        uDao.insert(bestUser);
        User foundUser = uDao.find(bestUser.getUsername());
        assertEquals(foundUser, bestUser);
    }

    @Test
    public void negRetrievalUser() throws DataAccessException {
        uDao.insert(bestUser);
        User foundUser = uDao.find("abcdef");
        assertNull(foundUser);
        assertThrows(DataAccessException.class, ()-> uDao.find(null), "importing a null string didn't throw an exception.");
    }

    @Test
    public void posValid() throws DataAccessException {
        uDao.insert(bestUser);
        boolean validActual = uDao.validUsernameAndPassword(bestUser.getUsername(), bestUser.getPassword());
        assertEquals(validActual, true);
    }

    @Test
    public void badValid() throws DataAccessException {
        uDao.insert(bestUser);
        boolean validActual = uDao.validUsernameAndPassword(bestUser.getPassword(), bestUser.getUsername());
        assertNotEquals(validActual, true);
    }
}


