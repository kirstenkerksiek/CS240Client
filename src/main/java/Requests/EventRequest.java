package Requests;

import Model.AuthToken;

/**
 * info from user about events
 */
public class EventRequest {

    private AuthToken authToken;
    private String eventID;

    public EventRequest() {
        authToken = null;
        eventID = null;
    }

    public EventRequest(AuthToken token, String id) {
        authToken = token;
        eventID = id;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken token) {
        authToken = token;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String id) {
        eventID = id;
    }


}
