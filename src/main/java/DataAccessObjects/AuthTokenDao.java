package DataAccessObjects;

import Model.AuthToken;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * get all the info from the sqlite database and create a new AuthToken in the local db
 * the authToken object is the auth Token that is being pulled from the sqliteDB
 */
public class AuthTokenDao {

    private final Connection conn;

    public AuthTokenDao(Connection conn)
    {
        this.conn = conn;
    }

    public void insert(AuthToken authToken) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO AuthTokens (AuthToken, Username) VALUES(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, authToken.getAuthID());
            stmt.setString(2, authToken.getUsername());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    public boolean isValidAuth(AuthToken authToken) throws DataAccessException {
        boolean valid = false;

        ResultSet rs = null;
        String sql = "SELECT * FROM AuthTokens;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            rs = stmt.executeQuery();
            while (rs.next()) {
               String authID = rs.getString("AuthToken");

               if(authToken.getAuthID().equals(authID)) {
                   valid = true;
                   break;
               }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while checking validity");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return valid;
    }

    public String getUsername(AuthToken authToken) throws DataAccessException {

        String username = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM AuthTokens WHERE AuthToken = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authToken.getAuthID());
            rs = stmt.executeQuery();
            String authID = rs.getString("AuthToken");
            if(authID.equals(authToken.getAuthID())) {
                username = rs.getString("Username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding username");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return username;
    }
}

