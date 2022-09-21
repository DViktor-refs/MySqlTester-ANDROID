package com.example.MySqlTester.mySqlStatements;

import com.example.MySqlTester.handlers.ConnectionHelper;

import java.sql.Connection;

public class Commons {

    private static Connection connection;
    private static String message;

    public static String setStatus() {
        connection = ConnectionHelper.getConnection();
        if (connection != null) {
            return "connected";
        }
        else {
            return "Disconnected";
        }
    }

    public static boolean isConnected() {
        return (connection != null) ? true:false;
    }

    public static void SetMessage(String message) {
        Commons.message = message;
    }

    public static String getMessage() {
        return message;
    }

}
