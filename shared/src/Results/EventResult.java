package Results;

import Model.Event;

/**
 * event info from user
 */
public class EventResult {

    private Event event;
    private String message;
    private String associatedUsername;
    private String eventID;
    private String personID;
    private float latitude;
    private float longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;
    /* error
        "Invalid auth token"
        "Invalid eventID parameter"
        "Requested event does not belong to this user"
        "Internal server error"
     */
    private boolean success;

    public EventResult() {}

    public EventResult(Event e, String errorMessage) {
        if (errorMessage.equals("No Errors")) {
            success = true;
            event = e;
            associatedUsername = event.getUsername();
            eventID = event.getEventID();
            message = null;
            personID = event.getPersonID();
            latitude = event.getLatitude();
            longitude = event.getLongitude();
            country = event.getCountry();
            city = event.getCity();
            eventType = event.getEventType();
            year = event.getYear();
        }
        else {
            message = errorMessage;
            event = null;
            success = false;
        }
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
