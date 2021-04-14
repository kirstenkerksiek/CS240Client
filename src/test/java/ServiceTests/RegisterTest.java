package ServiceTests;

import DataAccessObjects.DataAccessException;
import DataAccessObjects.Database;
import Requests.EventRequest;
import Requests.RegisterRequest;
import Results.EventResult;
import Results.RegisterResult;
import Services.ClearService;
import Services.RegisterService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterTest {
    Database db;
    RegisterRequest request;
    RegisterService service;

    @BeforeEach
    public void setUp() throws Exception {
        try
        {
            ClearService clearService = new ClearService();
            clearService.service();

            db = new Database();
            db.openConnection();

            request = new RegisterRequest("abby", "a", "abby", "adams", "a@email.com", "f");
            service = new RegisterService();

            db.closeConnection(true);
        }
        catch(DataAccessException except)
        {
            System.out.println("Error in setup:" + except.getMessage());
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
        RegisterResult result = service.service(request);
        assertEquals(null, result.getMessage());
        assertTrue(result.isSuccess());
        assertNotNull(result.getAuthtoken());
    }

    @Test
    public void negServiceTest() {
        request = new RegisterRequest("abby01", "a", "abby", "adams", "a@email.com", "f");
        RegisterResult result = service.service(request);

        request = new RegisterRequest("abby01", "a", "abby", "adams", "a@email.com", "f");
        result = service.service(request);
        assertNotNull(result.getMessage());
        assertFalse(result.isSuccess());

        request = new RegisterRequest("abby01", null, "abby", "adams", "a@email.com", "f");
        result = service.service(request);
        assertNotNull(result.getMessage());
        assertFalse(result.isSuccess());
    }
}
