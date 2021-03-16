package DataAccessObjects;
import Model.User;

import java.sql.*;

/**
 * access and mutate users in the database
 */

public class UserDao {
    private final Connection conn;


    public UserDao(Connection conn)
    {
        this.conn = conn;
    }

    /**
     * insert user
     * @param user
     * @throws DataAccessException
     */
    public void insert(User user) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO Users (Username, Password, EmailAddress, FName, LName, Gender, PersonID) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmailAddress());
            stmt.setString(4, user.getfName());
            stmt.setString(5, user.getlName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * find user
     * @param username
     * @return
     * @throws DataAccessException
     */
    public User find(String username) throws DataAccessException {
        User user;
        ResultSet rs = null;
        String sql = "SELECT * FROM Users WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("Username"), rs.getString("Password"),
                        rs.getString("EmailAddress"), rs.getString("FName"), rs.getString("LName"),
                        rs.getString("Gender"), rs.getString("PersonID"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding user");
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

    public boolean validUsernameAndPassword(String username, String password) throws DataAccessException {
        User user = find(username);
        if (user == null) {
            return false;
        }
        if (user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
}
