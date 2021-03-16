package Requests;

/**
 * used to request registration
 */
public class RegisterRequest {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;

    RegisterRequest(){
        username = null;
        password = null;
        email = null;
        firstName = null;
        lastName = null;
        gender = null;
    }

    public RegisterRequest(String uName, String word, String address, String fName, String lName, String sex) {
        username = uName;
        password = word;
        email = address;
        firstName = fName;
        lastName = lName;
        gender = sex;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        username = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String word) {
        password = word;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String address) {
        email = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        firstName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String name) {
        lastName = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String g) {
        gender = g;
    }
}
