package Services;

import DataAccessObjects.DataAccessException;
import DataAccessObjects.Database;
import Model.AuthToken;
import Model.Event;
import Requests.EventRequest;
import Results.EventResult;

/**
 * finds a specified event by event
 */
public class EventService {

    private Database db;
    private String message;

    public Database getDb() {
        return db;
    }

    public void setDb(Database d) {
        db = d;
    }

    public EventService(){
        db = new Database();
        message = "No Errors";
    }

    /**
     * find the event based on the event id
     * auth token required
     */
    public EventResult service(EventRequest request) {
        try {
            db.openConnection();
        }
        catch(DataAccessException e) {
            message= "Error: Internal Service Error @ openConnection @ EventService";
        }
        EventResult result = null;
        AuthToken authToken = request.getAuthToken();
        String eventID = request.getEventID();
        String username = null;
        Event event = null;

        if(message.equals("No Errors")) {
            try {
                if(db.authDao.isValidAuth(authToken)) {
                    username = db.authDao.getUsername(authToken);

                    if(db.eventDao.find(eventID).getUsername().equals(username)) {
                        event = db.eventDao.find(eventID);
                    }
                    else {
                        message = "Error: Requested event does not belong to this user";
                    }
                }
                else {
                    message = "Error: Invalid authorization";
                }
            } catch (DataAccessException e) {
                message = "Error: Internal server error" + e.getMessage();
            }
        }

        try {
            db.closeConnection(true);
        } catch (DataAccessException e) {
            message = "Error: Internal server error @ closeConnection @ EventService";
        }

        result = new EventResult(event, message);
        return result;
    }
}
