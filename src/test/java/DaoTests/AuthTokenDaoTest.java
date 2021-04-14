package DaoTests;

import DataAccessObjects.*;
import Model.AuthToken;
import Model.Person;
import Model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import Model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

//We will use this to test that our insert method is working and failing in the right ways
public class AuthTokenDaoTest {
    private Database db;
    private AuthTokenDao aDao;

    @BeforeEach
    public void setUp() throws DataAccessException
    {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new event with random data

        //Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Let's clear the database as well so any lingering data doesn't affect our tests
        db.clearTables();
        //Then we pass that connection to the EventDAO so it can access the database
        aDao = db.authDao;
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        //Here we close the connection to the database file so it can be opened elsewhere.
        //We will leave commit to false because we have no need to save the changes to the database
        //between test cases
        db.closeConnection(false);
    }

    @Test
    public void posValid() throws DataAccessException {
        User bestUser = new User("user", "password", "email", "kirsten", "kerksiek", "f", "abcdef");
        AuthToken auth = new AuthToken("abcdef", "user");
        aDao.insert(auth);
        boolean validActual = aDao.isValidAuth(auth);
        assertEquals(validActual, true);
    }

    @Test
    public void negValid() throws DataAccessException {
        User bestUser = new User("user", "password", "email", "kirsten", "kerksiek", "f", "abcdef");
        AuthToken auth = new AuthToken("abcdef", "user");
        aDao.insert(auth);
        AuthToken bad = new AuthToken("abcdef", "evil");
        boolean validActual = aDao.isValidAuth(bad);
        assertNotEquals(validActual, false);
        assertThrows(NullPointerException.class, ()-> aDao.isValidAuth(null), "importing a null string didn't throw an exception.");
    }

    @Test
    public void posGetUsername() throws DataAccessException {
        User bestUser = new User("user", "password", "email", "kirsten", "kerksiek", "f", "abcdef");
        AuthToken auth = new AuthToken("abcdef", "user");
        aDao.insert(auth);
        String username = aDao.getUsername(auth);
        assertEquals(bestUser.getUsername(), username);
    }

    @Test
    public void negGetUsername() throws DataAccessException {
        //tests with a null value
        assertThrows(NullPointerException.class, ()-> aDao.getUsername(null), "importing a null string didn't throw an exception.");
    }

    @Test
    public void insertPass() throws DataAccessException {
        AuthToken auth = new AuthToken("123456", "user1");
        //While insert returns a bool we can't use that to verify that our function actually worked
        //only that it ran without causing an error
        aDao.insert(auth);
        //So lets use a find method to get the event that we just put in back out
        String compareTest = aDao.getUsername(auth);
        //First lets see if our find found anything at all. If it did then we know that if nothing
        //else something was put into our database, since we cleared it in the beginning
        assertNotNull(compareTest);
        //Now lets make sure that what we put in is exactly the same as what we got out. If this
        //passes then we know that our insert did put something in, and that it didn't change the
        //data in any way
        assertEquals(auth.getUsername(), compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        AuthToken auth = new AuthToken("123456", "user1");
        //lets do this test again but this time lets try to make it fail
        //if we call the method the first time it will insert it successfully
        aDao.insert(auth);
        assertThrows(NullPointerException.class, ()-> aDao.insert(null));
    }
}
