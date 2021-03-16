package DataAccessObjects;

import Model.Event;
import Model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * access and mutate the elements in the persons table
 */
public class PersonDao {

    private final Connection conn;

    public PersonDao(Connection conn)
    {
        this.conn = conn;
    }

    /**
     * insert a person
     * @param person
     * @throws DataAccessException
     */
    public void insert(Person person) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO Persons (PersonID, Username, FName, LName, Gender, " +
                "FatherID, MotherID, SpouseID) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getUsername());
            stmt.setString(3, person.getfName());
            stmt.setString(4, person.getlName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * find a person based on a given id
     */
    public Person find(String pID) throws DataAccessException {
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM Persons WHERE PersonID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("PersonID"), rs.getString("Username"),
                        rs.getString("FName"), rs.getString("LName"),
                        rs.getString("Gender"), rs.getString("FatherID"), rs.getString("MotherID"),
                        rs.getString("SpouseID"));
                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    public void update(String member, String memberID, String personID) {
        try {
            String sql = null;

            if (member.equals("Mother")) {
                sql = "UPDATE Persons SET MotherID = ? Where PersonID = ?;";
            }
            else if (member.equals("Father")) {
                sql = "UPDATE Persons SET FatherID = ? Where PersonID = ?;";
            }
            else if (member.equals("Mother")) {
                sql = "UPDATE Persons SET MotherID = ? Where PersonID = ?;";
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, memberID);
                stmt.setString(2, personID);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new DataAccessException("Error encountered while updating family");
            }

        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    public void deletePersonsByUsername(String username) throws DataAccessException {

        String sql = "DELETE FROM Persons WHERE Username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while deleting persons");
        }
    }

    public Person[] findPersons(String username) throws DataAccessException {
        ArrayList<Person> persons = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT * FROM Persons WHERE Username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Person person;
                person = new Person(rs.getString("PersonID"), rs.getString("Username"),
                        rs.getString("FName"), rs.getString("LName"),
                        rs.getString("Gender"), rs.getString("FatherID"), rs.getString("MotherID"),
                        rs.getString("SpouseID"));
                persons.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        if (!(persons.isEmpty())) {
            Person[] returnPersons = new Person[persons.size()];

            for (int i = 0; i < persons.size(); i++) {
                returnPersons[i] = persons.get(i);
            }

            int i = 0;
            return returnPersons;
        }
        return null;
    }
}
