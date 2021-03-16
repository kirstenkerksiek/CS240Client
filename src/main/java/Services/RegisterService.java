package Services;

import DataAccessObjects.DataAccessException;
import DataAccessObjects.Database;
import Model.Person;
import Model.User;
import Requests.FillRequest;
import Requests.LoginRequest;
import Requests.RegisterRequest;
import Results.FillResult;
import Results.LoginResult;
import Results.RegisterResult;

import java.util.UUID;

/**
 * used as the middle man to facilitate registration
 */
public class RegisterService {

    private Database db;
    private String message;

    public RegisterService() {
        db = new Database();
        message = "No Errors";
    }

    /**
     * use it to register a new user
     * log them in
     * return the auth token
     */

    public RegisterResult service(RegisterRequest request) {
        try {
            db.openConnection();
        } catch (DataAccessException e) {
            message = "Error: Internal sever error @ RegisterService open connection";
        }
        String username = request.getUsername();
        String password = request.getPassword();
        String email = request.getEmail();
        String first = request.getFirstName();
        String last = request.getLastName();
        String gender = request.getGender();
        String authToken = null;
        String personID = null;

        RegisterResult result = null;

        if (message.equals("No Errors")) {
            if (username == null || password == null || email == null || first == null || last == null || gender == null) {
                message = "Error: Missing Request Property";
            }
            else if (!(gender.equals("m") || gender.equals("f"))) {
                message = "Error: Invalid Request Property";
            }
        }
        if (message.equals("No Errors")) {
            try {
                UUID uuid = UUID.randomUUID();
                personID = uuid.toString();

                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.setEmailAddress(email);
                user.setPersonID(personID);
                user.setfName(first);
                user.setlName(last);
                user.setGender(gender);

                Person person = new Person(personID, username, first, last, gender, null, null, null);

                try {
                    db.userDao.insert(user);
                }
                catch(DataAccessException e) {
                    message = "Error: that username is not available";
                }
                db.personDao.insert(person);

                db.closeConnection(true);
                db.openConnection();

                LoginRequest loginRequest = new LoginRequest(username, password);
                LoginService loginService = new LoginService();
                LoginResult loginResult = loginService.service(loginRequest);

                authToken = loginResult.getAuthtoken();

                FillRequest fillRequest = new FillRequest(username, 4);
                FillService fillService = new FillService();
                FillResult fillResult = fillService.service(fillRequest);
            }
            catch (DataAccessException e) {
                message = "Error: Internal server error: register, login, fill @ RegisterService";
            }
        }
        result = new RegisterResult(authToken, username, personID, message);
        try {
            db.closeConnection(true);
        } catch (DataAccessException e) {
            message = "Error: Internal server error: closing connection @ RegisterService";
        }
        return result;
    }

    public Database getDb() {
        return db;
    }

    public void setDb(Database d) {
        db = d;
    }


}
