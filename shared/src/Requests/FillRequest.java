package Requests;

import Model.AuthToken;

/**
 * info from users about fill
 */
public class FillRequest {

    //no AuthToken needed
    private String username;
    private int generations;

    public FillRequest() {
        username = null;
        generations = 0;
    }

    public FillRequest(String name, int gens) {
        username = name;
        generations = gens;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        username = name;
    }

    public int getGenerations() {
        return generations;
    }

    public void setGenerations(int gens) {
        generations = gens;
    }
}
