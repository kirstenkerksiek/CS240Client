package Services;

import DataAccessObjects.DataAccessException;
import DataAccessObjects.Database;
import Model.AuthToken;
import Model.Person;
import Requests.AllPersonRequest;
import Results.AllPersonResult;

/**
 * GET ALL THE ANCESTORS OF A SPECIFIED USER
 * Auth token required
 */
public class AllPersonService {



    private Database db;
    private String message;

    public Database getDb() {
        return db;
    }

    public void setDb(Database d) {
        db = d;
    }

    public AllPersonService(){
        message = "No Errors";
        db = new Database();
    }

    public AllPersonResult service(AllPersonRequest request) {
        AuthToken authToken = request.getAuthToken();
        AllPersonResult result = null;
        Person[] persons = null;
        String username = null;

        try {
            db.openConnection();
        } catch (DataAccessException e) {
            message = "Error: Internal Server @ Open Connection @ AllPersonService";
        }

        if (message.equals("No Errors")) {
            try {
                if (db.authDao.isValidAuth(authToken)) {
                    username = db.authDao.getUsername(authToken);
                    persons = db.personDao.findPersons(username);
                }
                else {
                    message = "Error: Invalid Authorization";
                }
            }
            catch (DataAccessException e) {
                message = "Error: Internal Server Error @ AllPersonService " + e.getMessage();
            }
        }

        try {
            db.closeConnection(true);
        } catch (DataAccessException e) {
            message = "Error: Internal server error while closing connection @ AllPersonService";
        }

        result = new AllPersonResult(persons, message);
        return result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
