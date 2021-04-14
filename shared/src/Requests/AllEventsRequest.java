package Requests;

import Model.AuthToken;

public class AllEventsRequest {

    private AuthToken authToken;

    public AllEventsRequest() {
        authToken = null;
    }

    public AllEventsRequest(AuthToken token) {
        authToken = token;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken token) {
        authToken = token;
    }
}
