package Results;

/**
 * returns info to user
 */
public class ClearResult {

    private String message; // "Internal Server Error"
    private boolean success;

    public ClearResult(){
        message = null;
        success = false;
    }

    public ClearResult(String errorMessage) {
        if (errorMessage.equals("No Errors")) {
            message = "Clear succeeded.";
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
