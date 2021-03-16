package ServiceTests;

import DataAccessObjects.DataAccessException;
import DataAccessObjects.Database;
import Model.User;
import Requests.RegisterRequest;
import Results.ClearResult;
import Results.RegisterResult;
import Services.ClearService;
import Services.RegisterService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;


public class ClearTest {
    private Database db;

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
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        //Here we close the connection to the database file so it can be opened elsewhere.
        //We will leave commit to false because we have no need to save the changes to the database
        //between test cases
    }

    @Test
    public void posServe() throws DataAccessException {
        User user = new User("user", "password", "email", "first", "last", "f", "123456");

        RegisterRequest request = new RegisterRequest("username", "password", "address", "firstName", "lastName", "f");
        RegisterService registerService = new RegisterService();
        RegisterResult registerResult = registerService.service(request);


        ClearService service = new ClearService();
        ClearResult result = service.service();

        //assertEquals(, );
    }
}
