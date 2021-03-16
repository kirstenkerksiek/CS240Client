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
    public void negFindUser() throws DataAccessException {
        User bestUser = new User("user", "password", "email", "kirsten", "kerksiek", "f", "abcdef");
        AuthToken auth = new AuthToken("abcdef", "user");
        aDao.insert(auth);
        //User foundUser = aDao.find("password");
        //assertNull(foundUser);
    }

    @Test
    public void posClear() throws DataAccessException {
        User bestUser = new User("user", "password", "email", "kirsten", "kerksiek", "f", "abcdef");
        AuthToken auth = new AuthToken("abcdef", "user");
        aDao.insert(auth);
        //aDao.clearTable();
        //assertNull(aDao.find(bestUser.getUsername()));
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
    }

    @Test
    public void posGetUsername() throws DataAccessException {
        User bestUser = new User("user", "password", "email", "kirsten", "kerksiek", "f", "abcdef");
        AuthToken auth = new AuthToken("abcdef", "user");
        aDao.insert(auth);
        String username = aDao.getUsername(auth);
        assertEquals(bestUser.getUsername(), username);
    }
}
