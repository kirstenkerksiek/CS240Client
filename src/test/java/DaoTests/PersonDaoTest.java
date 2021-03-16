package DaoTests;

import DataAccessObjects.DataAccessException;
import DataAccessObjects.Database;
import DataAccessObjects.EventDao;
import DataAccessObjects.PersonDao;
import Model.Event;
import Model.Person;
import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

//We will use this to test that our insert method is working and failing in the right ways
public class PersonDaoTest {
    private Database db;
    private Person bestPerson;
    private Person worstPerson;
    private PersonDao pDao;

    @BeforeEach
    public void setUp() throws DataAccessException
    {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new event with random data
        bestPerson = new Person("0000000c", "testuser", "abby", "smith", "f", "0000000a", "0000000b", "0000000d");
        worstPerson = new Person("0000000x", "testuser", "abby", "smith", "f", "0000000y", "0000000z", "null");
        //Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Let's clear the database as well so any lingering data doesn't affect our tests
        db.clearTables();
        //Then we pass that connection to the EventDAO so it can access the database
        pDao = new PersonDao(conn);
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
        pDao.insert(bestPerson);
        //So lets use a find method to get the event that we just put in back out
        Person compareTest = pDao.find(bestPerson.getPersonID());
        //First lets see if our find found anything at all. If it did then we know that if nothing
        //else something was put into our database, since we cleared it in the beginning
        assertNotNull(compareTest);
        //Now lets make sure that what we put in is exactly the same as what we got out. If this
        //passes then we know that our insert did put something in, and that it didn't change the
        //data in any way
        assertEquals(bestPerson, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        //lets do this test again but this time lets try to make it fail
        //if we call the method the first time it will insert it successfully
        pDao.insert(bestPerson);
        //but our sql table is set up so that "eventID" must be unique. So trying to insert it
        //again will cause the method to throw an exception
        //Note: This call uses a lambda function. What a lambda function is is beyond the scope
        //of this class. All you need to know is that this line of code runs the code that
        //comes after the "()->" and expects it to throw an instance of the class in the first parameter.
        assertThrows(DataAccessException.class, ()-> pDao.insert(bestPerson));
    }

    @Test
    public void posRetrievalPerson() throws DataAccessException {
        pDao.insert(bestPerson);
        Person foundPerson = pDao.find(bestPerson.getPersonID());
        assertEquals(bestPerson, foundPerson);
    }

    @Test
    public void negRetrievalPerson() throws DataAccessException {
        pDao.insert(bestPerson);
        Person foundUser = pDao.find("abcdef");
        assertNull(foundUser);
    }

    @Test
    public void posUpdate() throws DataAccessException {
        pDao.insert(bestPerson);
        Person mom = new Person("000d","testuser", "Mary", "Smith", "f", null, null, null);
        pDao.update("Mother", "000d", "0000000c");
        String bestPersonWithMom = pDao.find("0000000c").getMotherID();
        assertEquals(bestPersonWithMom, "000d");
    }

    @Test
    public void negUpdate() throws DataAccessException {
        pDao.insert(worstPerson);
        Person mom = new Person("000d","testuser", "Mary", "Smith", "f", null, null, null);
        pDao.update("Mother", "000d", "0000000w");
        String worstPersonWithWrongParent = pDao.find("0000000x").getFatherID();
        assertNotEquals(worstPersonWithWrongParent, "000d");
    }

    @Test
    public void posClearAll() throws DataAccessException {
        pDao.insert(bestPerson);
        db.clearTables();
        assertNull(pDao.find(bestPerson.getPersonID()));
    }

    @Test
    public void posDelete() throws DataAccessException {
        Person p1 = new Person("abc", "kirsten", "kirsten", "kerksiek", "f", null, null, null);
        Person p2 = new Person("def", "kirsten", "katanya", "kerksiek", "f", null, null, null);
        Person p3 = new Person("ghi", "kirsten", "kielle", "kerksiek", "f", null, null, null);
        Person p4 = new Person("xyz", "jacob", "jacob", "michael", "m", null, null, null);
        pDao.insert(p1);
        pDao.insert(p2);
        pDao.insert(p3);
        pDao.insert(p4);
        pDao.deletePersonsByUsername("kirsten");

        assertEquals(pDao.findPersons("jacob").length, 1);
    }

    @Test
    public void negDelete() throws DataAccessException {
        Person p1 = new Person("abc", "kirsten", "kirsten", "kerksiek", "f", null, null, null);
        Person p2 = new Person("def", "kirsten", "katanya", "kerksiek", "f", null, null, null);
        Person p3 = new Person("ghi", "kirsten", "kielle", "kerksiek", "f", null, null, null);
        Person p4 = new Person("xyz", "jacob", "jacob", "michael", "m", null, null, null);
        pDao.insert(p1);
        pDao.insert(p2);
        pDao.insert(p3);
        pDao.insert(p4);
        pDao.deletePersonsByUsername("kirsten");
        assertNull(pDao.findPersons("kirsten"));
    }

    @Test
    public void posFindPersons() throws DataAccessException {
        Person p1 = new Person("abc", "kirsten", "kirsten", "kerksiek", "f", null, null, null);
        Person p2 = new Person("def", "kirsten", "katanya", "kerksiek", "f", null, null, null);
        Person p3 = new Person("ghi", "kirsten", "kielle", "kerksiek", "f", null, null, null);
        Person p4 = new Person("xyz", "jacob", "jacob", "michael", "m", null, null, null);
        pDao.insert(p1);
        pDao.insert(p2);
        pDao.insert(p3);
        pDao.insert(p4);
        Person[] persons = pDao.findPersons("kirsten");

        assertEquals(persons.length, 3);
    }

    @Test
    public void negFindPersons() throws DataAccessException {
        Person p1 = new Person("abc", "kirsten", "kirsten", "kerksiek", "f", null, null, null);
        Person p2 = new Person("def", "kirsten", "katanya", "kerksiek", "f", null, null, null);
        Person p3 = new Person("ghi", "kirsten", "kielle", "kerksiek", "f", null, null, null);
        Person p4 = new Person("xyz", "jacob", "jacob", "michael", "m", null, null, null);
        pDao.insert(p1);
        pDao.insert(p2);
        pDao.insert(p3);
        pDao.insert(p4);
        Person[] persons = pDao.findPersons("katanya");
        assertNull(persons);
    }
}



