package Model;

import com.google.gson.annotations.SerializedName;

/**
 * personID is the primary key
 * username is a foreign key to user
 * fName, lName is first and last name respectively
 * gender will either be a "f" (female) or "m" (male)
 * fatherID is a foreign key to person for the dad
 * motherID is a foreign key to person for the mom
 * spouseID is a foreign key to person for the spouse
 */
public class Person {

    private String personID;
    @SerializedName("associatedUsername")
    private String username;
    @SerializedName("firstName")
    private String fName;
    @SerializedName("lastName")
    private String lName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouseID;

    public Person(){}

    public Person(String pID, String un, String f, String l, String g, String fID, String mID, String sID) {
        personID = pID;
        username = un;
        fName = f;
        lName = l;
        gender = g;
        fatherID = fID;
        motherID = mID;
        spouseID = sID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String id) {
        personID = id;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof Person) {
            Person oPerson = (Person) o;
            return oPerson.getPersonID().equals(getPersonID()) &&
                    oPerson.getUsername().equals(getUsername()) &&
                    oPerson.getfName().equals(getfName()) &&
                    oPerson.getlName().equals(getlName()) &&
                    oPerson.getGender().equals(getGender()) &&
                    oPerson.getFatherID().equals(getFatherID()) &&
                    oPerson.getMotherID().equals(getMotherID()) &&
                    oPerson.getSpouseID().equals(getSpouseID());
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }
}
