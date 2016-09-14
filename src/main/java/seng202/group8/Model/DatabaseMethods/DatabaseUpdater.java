package seng202.group8.Model.DatabaseMethods;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Callum on 13/09/16.
 */
public class DatabaseUpdater {

    private String generateSQL(String table, String updateString, String paramID, String searchParam) {

        String sql = "UPDATE " + table + " SET " + updateString + " WHERE " + paramID + " = '" + searchParam + "';";
        return sql;

    }

    private String createUpdateString(String column, String value) {

        String updateString = column + "='" + value + "'";
        return updateString;

    }

    private String getUpdateCommands(ArrayList<ArrayList<String>> updateParams) {

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
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            System.out.println("ERROR " + e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public void updateAirline(Connection conn, ArrayList<ArrayList<String>> updateParams, String key) {

        String updates = getUpdateCommands(updateParams);
        String sql = generateSQL("airline", updates, "airlineid", key);
        updateTable(conn, sql);

    }

    public void updateAirport(Connection conn, ArrayList<ArrayList<String>> updateParams, String key) {

        String updates = getUpdateCommands(updateParams);
        String sql = generateSQL("airport", updates, "airportid", key);
        updateTable(conn, sql);
    }

    public void updateRoute(Connection conn, ArrayList<ArrayList<String>> updateParams, String key) {
        String updates = getUpdateCommands(updateParams);
        String sql = generateSQL("route", updates, "routeid", key);
        updateTable(conn, sql);
    }

}
