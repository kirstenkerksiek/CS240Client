package Results;

import Model.AuthToken;

/**
 * used to check validity and presence of a login token
 * data member of authToken
 */
public class LoginResult {

    private String authtoken;
    private String username;
    private String personID;
    private String message;
    /* messages:
        "Request property missing or has invalid value"
        "Internal Server Error"
     */
    private boolean success;

    public LoginResult(){
        authtoken = null;
        username = null;
        personID = null;
        message = null;
        success = false;
    }

    public LoginResult(AuthToken token, String name,  String id, String errorMessage) {
        if (errorMessage.equals("No Errors")) {
            authtoken = token.getAuthID();
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

    public void setSuccess(boolean isSuccessful) {
        success = isSuccessful;
    }
}
