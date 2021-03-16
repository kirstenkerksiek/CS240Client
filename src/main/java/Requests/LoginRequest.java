package Requests;

import Model.AuthToken;

/**
 * used to check if the the login is approved or not
 */
public class LoginRequest {

    private String username;
    private String password;

    LoginRequest(){
        username = null;
        password = null;
    }

    public LoginRequest(String name, String word) {
        username = name;
        password = word;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String u) {
        username = u;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String p) {
        password = p;
    }
}
