package Requests;

import Model.AuthToken;

/**
 * info from users about person
 */
public class PersonRequest {

    private AuthToken authToken;
    private String personID;

    public PersonRequest(){
        authToken = null;
        personID = null;
    }

    public PersonRequest(AuthToken token, String id) {
        authToken = token;
        personID = id;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken token) {
        authToken = token;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String id) {
        personID = id;
    }
}
