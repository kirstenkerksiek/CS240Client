package Results;

/**
 * info to users about result
 */
public class LoadResult {

    private String message;
    /* errors
        Invalid request data
        Internal Server Error
     */
    private boolean success;

    public LoadResult() {
        message = null;
        success = false;
    }

    public LoadResult(int numUsers, int numPersons, int numEvents, String errorMessage) {
        if (errorMessage.equals("No Errors")) {
            message =  "Successfully added " + numUsers + " users, " + numPersons + " persons, and " + numEvents + " events to the database.";
            success = true;
        }
        else {
            message = errorMessage;
            success = false;
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
