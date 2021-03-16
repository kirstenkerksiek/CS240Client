package Results;

/**
 * info to user about fill
 */
public class FillResult {

    private String message;
    private boolean success;

    public FillResult() {
        message = null;
        success = false;
    }

    public FillResult(String errorMessage, int numPersons, int numEvents) {
        if (errorMessage.equals("No Errors")) {
            success = true;
            message = "Successfully added " + numPersons + " persons and " + numEvents + " events to the database.";
        }
        else {
            success = false;
            message = errorMessage;
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String errorMessage) {
        message = errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean worked) {
        success = worked;
    }
}
