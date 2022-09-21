package com.example.MySqlTester.mySqlStatements;

import static com.example.MySqlTester.mySqlStatements.Commons.isConnected;

import android.util.Log;
import com.example.MySqlTester.handlers.ConnectionHelper;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class InsertInto {

    private Statement statement;
    private Connection connection;
    boolean isSuccessful;

    public boolean executeStatement(String tableName, List<String> columns, List<String> values) {
        String query;
        connection = ConnectionHelper.getConnection();

        if (isConnected()) {
            if (!columns.isEmpty() && !values.isEmpty()) {
                query = createQuery(tableName, columns, values);
                isSuccessful = createStatement(query);
            }
        }
        else {
            Commons.SetMessage("Invalid connection. Please check the host settings.");
            isSuccessful = false;
        }
        return isSuccessful;
    }

    private String createQuery(String tableName, List<String> columns, List<String> values) {
        String result = "INSERT INTO " + tableName;
        result += addColumns(columns);
        result += " VALUES ";
        result += addValues(values);
        Log.d("query: ", result);
        return result;
    }

    private String addValues(List<String> values) {
        String result = "";
        if (!values.isEmpty() || values != null) {
            result += " (";
            for (int i = 0; i < values.size(); i++) {
                if (i == values.size()-1) {
                    result += "\"" + values.get(i) + "\"" + ")";
                }
                else {
                    result += "\"" + values.get(i) + "\"" + ", ";
                }
            }
            Log.d("result after values lists : ", result);
        }
        return result;
    }

    private String addColumns(List<String> columns) {
        String result = "";
        if (!columns.isEmpty() || columns != null) {
            result += " (";
            for (int i = 0; i < columns.size(); i++) {
                if (i == columns.size()-1) {
                    result += "" + columns.get(i) +  ")";
                }
                else {
                    result += "" + columns.get(i) +  ", ";
                }
            }
            Log.d("result after column lists : ", result);
        }
        return result;
    }

    private boolean createStatement(String query) {
        Log.d("ez a kuery", "createStatement: " + query);
        if (!query.trim().isEmpty()) {
            Log.d("TAG", "createStatement: " + "query length > 0");
            try {
                statement = connection.createStatement();
                statement.executeUpdate(query);
                Commons.SetMessage("Completted.");
                Log.d("TAG", "createStatement: " + "minden fasza");
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                Commons.SetMessage("Error: " + e.getMessage());
                return false;
            }
        }
        else {
            Log.d("TAG", "createStatement: " + "query length < 0");
            Commons.SetMessage("Please fill value fields.");
            return false;
        }
    }


}
