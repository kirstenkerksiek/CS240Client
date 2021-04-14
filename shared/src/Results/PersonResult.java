package Results;

import Model.Person;

/**
 * info to user about results
 */
public class PersonResult {

    private Person person;
    private String message;
    private String associatedUsername;
    private String personID;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouseID;
    /* errors
      "Invalid auth token"
      "Invalid personID parameter"
      "Requested person does not belong to this user"
      "Internal server error"
     */
    private boolean success;

    public PersonResult() {
    }

    public PersonResult(Person p, String errorMessage) {
        if (errorMessage.equals("No Errors")) {
            person = p;
            associatedUsername = p.getUsername();
            personID = p.getPersonID();
            firstName = p.getfName();
            lastName = p.getlName();
            gender = p.getGender();
            fatherID = p.getFatherID();
            motherID = p.getMotherID();
            spouseID = p.getSpouseID();
            message = null;
            success = true;
        } else {
            person = null;
            message = errorMessage;
            success = false;
        }
    }


    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }
}