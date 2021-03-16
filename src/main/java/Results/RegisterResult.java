package Results;

/**
 * used to check validity and presence of a registration token
 * data member of authToken
 */
public class RegisterResult {

    private String authtoken;
    private String username;
    private String personID;
    private String message;
    /* possible errors
        "Request property missing or has invalid value"
        "Username already taken by another user"
        "Internal Server Error"
     */
    private boolean success;

    public RegisterResult(){
        this.authtoken = null;
        this.username = null;
        this.personID = null;
        this.message = null;
        success = false;
    }

    public RegisterResult(String token, String name, String id, String errorMessage) {
        if (errorMessage.equals("No Errors")) {
            authtoken = token;
            username = name;
            personID = id;
            message = null;
            success = true;
        }
        else {
            authtoken = null;
            username = null;
            personID = null;
            message = errorMessage;
            success = false;
        }
    }


    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String token) {
        authtoken = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        username = name;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String id) {
        personID = id;
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

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
