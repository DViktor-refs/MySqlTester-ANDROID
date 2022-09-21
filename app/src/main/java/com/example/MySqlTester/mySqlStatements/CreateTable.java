package com.example.MySqlTester.mySqlStatements;

import android.util.Log;

import com.example.MySqlTester.handlers.ConnectionHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CreateTable {

    private Connection connection;
    private Statement statement;
    private List<String> spinnerList = new ArrayList<>();
    private List<String> editTextFields = new ArrayList<>();
    private List<Boolean> radioButtonStateList = new ArrayList<>();
    private String mysqlMessage="";
    private String query = "";
    private boolean isSuccessful;

    public boolean createTable(String tableName,
                               List<String> editTextFields,
                               List<String> spinnerList,
                               List<Boolean> radioButtonStateList) {

        this.spinnerList = spinnerList;
        this.editTextFields = editTextFields;
        this.radioButtonStateList = radioButtonStateList;

        connection = ConnectionHelper.getConnection();

        if (checkNeededLists() && isConnected()) {
            query = buildCreateTableQuery(tableName);
            isSuccessful = createStatement(query);
        }
        else {
            if (!isConnected()) {
                mysqlMessage = "Invalid connection. Please check the host settings.";
            } else if (!checkNeededLists()) {
                mysqlMessage = "Missing parameters error. ";
            }
            isSuccessful = false;
        }
        return isSuccessful;
    }

    private String buildCreateTableQuery(String tableName) {
        String result = "CREATE TABLE " + tableName + "(";
        int index = 0;

        for (int i = 0; i < 3; i++) {
            result += getNameOfColumn(i).replaceAll("\\s","") + " " + spinnerList.get(i) + " ";
            result += radioButtonStateList.get(index) ? "PRIMARY KEY " : "";
            result += radioButtonStateList.get(index+1) ? "AUTO_INCREMENT " : "";
            result += radioButtonStateList.get(index+2) ? "NOT NULL " : "";
            result += (i==2) ? "" : ", ";
            index = index +3;

        }
        result += ")";
        return result;
    }

    private boolean createStatement(String query) {
        try {
            statement = connection.createStatement();
            Log.d("query = ", "createStatement: " + query);
            statement.executeUpdate(query);
            Commons.SetMessage("Completted.");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            Commons.SetMessage("Error: " + e.getMessage());
            return false;
        }
    }

    private String getNameOfColumn(int i) {
        return editTextFields.get(i);
    }

    private boolean checkNeededLists() {
        boolean result = false;
        if(!spinnerList.isEmpty() &&
                !editTextFields.isEmpty() &&
                !radioButtonStateList.isEmpty()) {
            result = true;
        }
        return result;
    }

    private boolean isConnected() {
        return (connection != null) ? true:false;
    }



}
