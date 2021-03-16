package Model;


import com.google.gson.annotations.SerializedName;

/**
 * username is the primary key
 * password is a string
 * emailAddress is the client's password
 * fName, lName are the first and last names
 * gender will be a "f" or "m"
 * personID is a foreign key for persons
 */
public class User {

    private String username;
    private String password;
    @SerializedName("email")
    private String emailAddress;
    @SerializedName("firstName")
    private String fName;
    @SerializedName("lastName")
    private String lName;
    private String gender;
    private String personID;

    public User(String username, String password, String emailAddress, String fName, String lName, String gender, String personID){
        this.username = username;
        this.password = password;
        this.emailAddress = emailAddress;
        this.fName = fName;
        this.lName = lName;
        this.gender = gender;
        this.personID = personID;
    };

    public User() {}


    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof User) {
            User oUser = (User) o;
            return oUser.getUsername().equals(getUsername()) &&
                    oUser.getPassword().equals(getPassword()) &&
                    oUser.getEmailAddress().equals(getEmailAddress()) &&
                    oUser.getfName().equals(getfName()) &&
                    oUser.getlName().equals(getlName()) &&
                    oUser.getGender().equals(getGender()) &&
                    oUser.getPersonID().equals(getPersonID());
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
