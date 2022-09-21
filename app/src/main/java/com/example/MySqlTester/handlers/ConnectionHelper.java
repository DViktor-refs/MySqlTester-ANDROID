package com.example.MySqlTester.handlers;

import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConnectionHelper {

    static TextView status;
    static Connection connection = null;

    public ConnectionHelper() { }

    public void setStatusTextView(TextView status) {
        this.status = status;
    }

    public static Connection connection(String hostName, String port, String dataBaseName, String userName, String password) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String connectionUrl;
        String message = "Connected.";

        try{
            //Class.forName("com.mysql.jdbc.Driver");
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionUrl = "jdbc:mysql://" + hostName + "/" + dataBaseName;
            Log.d("connurl", "connection: " + connectionUrl);
            connection = DriverManager.getConnection(connectionUrl, userName, password);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            message = ("Error: Class not found.");
            connection = null;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            message = ("Error: " + throwables.getMessage());
            connection = null;
        }

        status.setText(message);

        return connection;
    }

    public static Connection getConnection() {
        return connection;
    }

    public static DatabaseMetaData getDatabaseMetaData() {
        DatabaseMetaData dbmd = null;

        if (connection != null) {
            try {
                dbmd = connection.getMetaData();
            } catch (SQLException e) {
                status.setText("Erro: " + e.getMessage());
                e.printStackTrace();
            }
        }
        else {
            status.setText("Disconnected.");
        }
        return dbmd;

    }

    public static ResultSetMetaData getResultSetMetaData(String tableName) {
        Statement stmt;
        ResultSet rs;
        ResultSetMetaData rsmd = null;
        if (connection != null) {
            try {
                stmt = connection.createStatement();
                rs = stmt.executeQuery("SELECT * FROM " + tableName);
                rsmd = rs.getMetaData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return (rsmd != null) ? rsmd : null;
    }




}
