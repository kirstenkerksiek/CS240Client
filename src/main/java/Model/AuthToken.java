package Model;

/**
 * auth is the unique primary key
 * username is the foreign key for users
 * time is the time the token was given
 */
public class AuthToken {

    private String authID;
    private String username;

    public AuthToken(){}

    public AuthToken(String auth, String name) {
        authID = auth;
        username = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof AuthToken) {
            AuthToken oAuthToken = (AuthToken) o;
            return oAuthToken.getAuthID().equals(getAuthID()) &&
                    oAuthToken.getUsername().equals(getUsername());
        } else {
            return false;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthID() {
        return authID;
    }

    public void setAuthID(String authID) {
        this.authID = authID;
    }
}
