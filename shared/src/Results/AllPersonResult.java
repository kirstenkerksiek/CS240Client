package Results;

import Model.Person;

public class AllPersonResult {

    private Person[] data;
    private String message;
    /* error
        "Invalid auth token"
        "Internal server error"
     */
    private boolean success;

    public AllPersonResult(){}

    public AllPersonResult(Person[] persons, String errorMessage) {
        if (errorMessage.equals("No Errors")) {
            data = persons;
            success = true;
            message = null;
        }
        else {
            data = null;
            success = false;
            message = errorMessage;
        }
    }

    public Person[] getData() {
        return data;
    }

    public void setData(Person[] data) {
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
