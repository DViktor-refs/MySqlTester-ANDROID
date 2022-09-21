package com.example.MySqlTester.mySqlStatements;

import com.example.MySqlTester.handlers.ConnectionHelper;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DropTable {

    public String mysqlMessage = "";
    private String query = "DROP TABLE ";
    private Statement statement;
    private Connection connection;
    boolean isSuccessful;

    public boolean dropTable(String tableName) {

        connection = ConnectionHelper.getConnection();

        if (isConnected()) {
            query += tableName;
            isSuccessful = createStatement(query);
        }
        else {
            mysqlMessage = "Invalid connection. Please check the host settings.";
            isSuccessful = false;
        }
        return isSuccessful;
    }

    private boolean createStatement(String query) {
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
            mysqlMessage = "Completted.";
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            mysqlMessage = "Error: " + e.getMessage();
            return false;
        }
    }

    private boolean isConnected() {
        return (connection != null) ? true:false;
    }

}
