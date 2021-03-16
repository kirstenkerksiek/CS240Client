package Services;

import DataAccessObjects.DataAccessException;
import DataAccessObjects.Database;
import Model.AuthToken;
import Model.Person;
import Requests.PersonRequest;
import Results.PersonResult;

/**
 * find a person based on a personID
 */
public class PersonService {

    private Database db;
    private String message;

    public Database getDb() {
        return db;
    }

    public void setDb(Database d) {
        db = d;
    }

    public PersonService() {
        db = new Database();
        message= "No Errors";
    }

    /**
     * search and find a person based on their id
     * auth token required
     */
    public PersonResult service(PersonRequest request) {
        PersonResult result = null;
        String personID = request.getPersonID();
        AuthToken authToken = request.getAuthToken();
        String username = null;
        Person person = null;

        try {
            db.openConnection();
        }
        catch(DataAccessException e) {
            message = "Error: Internal Server Error @ openConnection @ PersonService";
        }

        if (message.equals("No Errors")) {
            try {
                if (db.personDao.find(personID) != null) {
                    //first check valid authorization
                    if (db.authDao.isValidAuth(authToken)) {
                        username = db.authDao.getUsername(authToken);
                        if (db.personDao.find(personID).getUsername().equals(username)) {
                            person = db.personDao.find(personID);
                        } else {
                            message = "Error: Requested Person does not belong to this user";
                        }
                    } else {
                        message = "Error: Invalid Authorization";
                    }
                }
                else {
                    message = "Error: Person does not exist";
                }
            }
            catch (DataAccessException e) {
                message = "Error: Internal Server Error @ get Person @ PersonService";
            }
        }

        try {
            db.closeConnection(true);
        } catch (DataAccessException e) {
            message = "Error: Close Connection @ Person Service";
        }
        result = new PersonResult(person, message);
        return result;
    }
}
