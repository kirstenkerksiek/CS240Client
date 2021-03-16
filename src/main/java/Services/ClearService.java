package Services;

import DataAccessObjects.DataAccessException;
import DataAccessObjects.Database;
import Results.ClearResult;

/**
 * clear EVERYTHING
 */
public class ClearService {

    public ClearService(){}

    /**
     * CLEAR EVERYTHING
     * DELETE ALL DATA
     */
    public ClearResult service() {
        ClearResult result = null;
        Database db = new Database();

         try {
            db.openConnection();
            db.clearTables();
            db.closeConnection(true); //
            result = new ClearResult("No Errors");

         } catch (DataAccessException e) {
             result = new ClearResult("Error: Internal Server Error: " + e.getMessage());
         }

         return result;
    }
}
