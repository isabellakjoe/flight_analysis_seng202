package seng202.group8.Model.DatabaseMethods;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Callum on 13/09/16.
 */
public class DatabaseUpdater {

    private String generateSQL(String table, String updateString, String paramID, String searchParam) {
        // Method used to ToJSONArray an sql query to update values in the database
        String sql = "UPDATE " + table + " SET " + updateString + " WHERE " + paramID + " = '" + searchParam + "';";
        return sql;

    }

    private String createUpdateString(String column, String value) {
        // Method used to add update parameters to an sql string
        String updateString = column + "='" + value + "'";
        return updateString;

    }

    private String getUpdateCommands(ArrayList<ArrayList<String>> updateParams) {
        // Method used to generate of a string containing values to update
        String sql = "";

        if (updateParams.size() == 1) {
            //For the case where there is only one updatable parameter, do this here
            sql = createUpdateString(updateParams.get(0).get(0), updateParams.get(0).get(1));
        } else {
            int size = updateParams.size();
            for (int i = 0; i < size - 1; i++) {
                sql += createUpdateString(updateParams.get(i).get(0), updateParams.get(0).get(1));
                sql += ",";
            }
            sql += createUpdateString(updateParams.get(size - 1).get(0), updateParams.get(size - 1).get(1));
        }

        return sql;

    }

    private void updateTable(Connection conn, String sql) {
        //Method used to commit an update to the database
        try {
            System.out.println("Updating");
            System.out.println(sql);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }
    }

    /**
     * Method used to update an airline in the database
     *
     * @param conn         a static connection to the database
     * @param updateParams a list of parameters to be updated
     * @param key          the id of the airline to be updated
     */
    public void updateAirline(Connection conn, ArrayList<ArrayList<String>> updateParams, String key) {

        String updates = getUpdateCommands(updateParams);
        String sql = generateSQL("airline", updates, "airlineid", key);
        updateTable(conn, sql);

    }

    /**
     * Method used to update an airport in the database
     *
     * @param conn         a static connection to the database
     * @param updateParams a list of parameters to be updated
     * @param key          the id of the airport to be updated
     */
    public void updateAirport(Connection conn, ArrayList<ArrayList<String>> updateParams, String key) {

        String updates = getUpdateCommands(updateParams);
        String sql = generateSQL("airport", updates, "airportid", key);
        updateTable(conn, sql);
    }

    /**
     * Method used to update a route in the database
     *
     * @param conn         a static connection to the database
     * @param updateParams a list of parameters to be updated
     * @param key          the id of the airport to be updated
     */
    public void updateRoute(Connection conn, ArrayList<ArrayList<String>> updateParams, String key) {
        String updates = getUpdateCommands(updateParams);
        String sql = generateSQL("route", updates, "routeid", key);
        updateTable(conn, sql);
    }

}
