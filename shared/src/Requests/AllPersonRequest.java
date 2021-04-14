package Requests;

import Model.AuthToken;

public class AllPersonRequest {

    private AuthToken authToken;

    public AllPersonRequest() {
        authToken = null;
    }

    public AllPersonRequest(AuthToken token) {
        authToken = token;
    }

    public void setAuthToken(AuthToken token) {
        authToken = token;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }
}
