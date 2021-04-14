package Services;

import DataAccessObjects.DataAccessException;
import DataAccessObjects.Database;
import Model.AuthToken;
import Model.Event;
import Requests.AllEventsRequest;
import Results.AllEventsResult;
import Results.AllPersonResult;

/**
 * returns all events of the all the users family member
 */
public class AllEventsService {

    private Database db;
    private String message;

    public AllEventsService(){
        db = new Database();
        message = "No Errors";
    }

    public Database getDb() {
        return db;
    }

    public void setDb(Database d) {
        db = d;
    }


    /**
     * get all the events of a specified user's family members
     */
    public AllEventsResult service(AllEventsRequest request) {
        AllEventsResult result = null;
        AuthToken authToken = request.getAuthToken();
        Event[] events = null;
        String username = null;

        try {
            db.openConnection();
        } catch (DataAccessException e) {
            message = "Error: Internal Server Error @ AllEventsService" + e.getMessage();
        }

        if (message.equals("No Errors")) {
            try {
                if (db.authDao.isValidAuth(authToken)) {
                    username = db.authDao.getUsername(authToken);
                    events = db.eventDao.findEvents(username);
                }
                else {
                    message = "Error: Invalid Authorization";
                }
            }
            catch (DataAccessException e) {
                message = "Error: Internal Server Error @ AllEventsService " + e.getMessage();
            }
        }

        try {
            db.closeConnection(true);
        } catch (DataAccessException e) {
            message = "Error: Internal Server Error @ AllEventsService" + e.getMessage();
        }
        result = new AllEventsResult(events, message);
        return result;
    }
}
