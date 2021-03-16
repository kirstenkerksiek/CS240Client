package Services;

import DataAccessObjects.DataAccessException;
import DataAccessObjects.Database;
import Model.AuthToken;
import Model.User;
import Requests.LoginRequest;
import Results.LoginResult;

import java.util.UUID;

/**
 * use this to facilitate the login process for the clients
 */
public class LoginService {

    private Database db;
    private String message;

    public LoginService(){
        db = new Database();
        message = "No Errors";
    }

    public Database getDb() {
        return db;
    }

    public void setDb(Database d) {
        db = d;
    }

    public LoginResult service(LoginRequest request) {
        try {
            db.openConnection();
        }
        catch (DataAccessException e) {
            message = "Error: Internal sever error : open connection @ LoginService";
        }

        LoginResult result = null;
        String username = request.getUsername();
        String password = request.getPassword();
        String personID = null;
        AuthToken token = null;

        if (message.equals("No Errors")) {
            try {
                if (username.equals("") || password.equals("")) {
                    message = "Error: Request property missing";
                }
                else if (db.userDao.validUsernameAndPassword(username, password)) {
                    User user = db.userDao.find(username);
                    personID = user.getPersonID();

                    UUID uuid = UUID.randomUUID();
                    token = new AuthToken(uuid.toString(), username);
                    db.authDao.insert(token);
                    message = "No Errors";
                }
                else {
                    message = "Error: Invalid value";
                }
            }
            catch (DataAccessException e) {
                message = "Error: Internal service error @ inserting into authDao @ LoginService";
            }
        }


        try {
            db.closeConnection(true);
        } catch (DataAccessException e) {
            message = "Error: Internal server error : close connection @ login service";
        }
        result = new LoginResult(token, username, personID, message);
        return result;
    }
}
