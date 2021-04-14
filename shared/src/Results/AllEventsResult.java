package Results;

import Model.Event;

public class AllEventsResult {
    private Event[] data;
    private String message;
    /* error
        "Invalid auth token"
        "Internal server error"
     */
    private boolean success;

    public AllEventsResult() {}

    public AllEventsResult(Event[] events, String errorMessage) {
        if (errorMessage.equals("No Errors")) {
            data = events;
            message = null;
            success = true;
        }
        else {
            data = null;
            message = errorMessage;
            success = false;
        }
    }

    public Event[] getData() {
        return data;
    }

    public void setData(Event[] data) {
        this.data = data;
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
