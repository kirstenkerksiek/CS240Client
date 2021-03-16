package Services;

import DataAccessObjects.DataAccessException;
import DataAccessObjects.Database;
import Model.Event;
import Model.Person;
import Model.User;
import Requests.LoadRequest;
import Results.ClearResult;
import Results.LoadResult;

/**
 * clear all the data
 * and load in new data
 */
public class LoadService {

    private Database db;
    private String message;

    public Database getDb() {
        return db;
    }

    public void setDb(Database d) {
        db = d;
    }

    public LoadService(){
        db = new Database();
        message = "No Errors";
    }

    public LoadResult service(LoadRequest request) {
        try {
            db.openConnection();
        } catch (DataAccessException e) {
            message = "Error: Internal server error @ openConnection @ LoadService";
        }

        LoadResult result = null;
        //use clear result & service
        ClearService clearService = new ClearService();
        ClearResult ClearResult = clearService.service();


        User[] users = request.getUsers();
        Person[] persons = request.getPersons();
        Event[] events = request.getEvents();

        int numUsers = 0;
        int numPersons = 0;
        int numEvents = 0;

        if(message.equals("No Errors")) {
            if (users == null || persons == null || events == null) {
                message = "Error: Invalid request data @ LoadService";
            }
            else {
                try {
                    for (int i = 0; i < users.length; i++) {
                        db.userDao.insert(users[i]);
                        numUsers++;
                    }
                    for (int i = 0; i < persons.length; i++) {
                        db.personDao.insert(persons[i]);
                        numPersons++;
                    }
                    for (int i = 0; i < events.length; i++) {
                        db.eventDao.insert(events[i]);
                        numEvents++;
                    }
                } catch (DataAccessException e) {
                    message = "Error: Internal server error @ LoadService Insertion";
                }
            }
        }
        try {
            db.closeConnection(true);
        } catch (DataAccessException e) {
            message = "Error: Internal server error @ closeConnection @ Load Service";
        }
        result = new LoadResult(numUsers, numPersons, numEvents, message);
        return result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
