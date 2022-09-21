package com.example.MySqlTester.mySqlStatements;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static com.example.MySqlTester.mySqlStatements.Commons.*;

import android.renderscript.Sampler;
import android.util.Log;

import com.example.MySqlTester.R;
import com.example.MySqlTester.handlers.ConnectionHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SelectFrom {

    Statement statement;
    Connection connection;
    String query;

    public boolean executeStatement(List<String> selectedColumnNames, String tableName) {

        connection = ConnectionHelper.getConnection();
        boolean isSuccessful;

        if(isConnected() )  {
            if (!selectedColumnNames.isEmpty()) {
                query = "SELECT ";
                int length = selectedColumnNames.size()-1;
                for (int i = 0; i < selectedColumnNames.size()-1; i++) {
                    query += selectedColumnNames.get(i) + ", ";
                }
                query += selectedColumnNames.get(length) +  " FROM " + tableName;
                isSuccessful = initStatement(query);
                Commons.SetMessage("Completted.");
            }
            else {
                Commons.SetMessage("choose at least one column.");
                isSuccessful = false;
            }
        }
        else {
            isSuccessful = false;
            Commons.SetMessage("Invalid connection. Please check the host settings.");
        }
        return isSuccessful;
    }

    public List<String> getValuesFromResultSet(List<String> columnNames) {
        List<String> result = new ArrayList<>();

        try {
            while (getResultSet().next()) {
                for (int i = 0; i < columnNames.size(); i++) {
                    result.add(getResultSet().getString(columnNames.get(i)));
                    if (i == columnNames.size()-1) {
                        result.add("NEWLINE");
                    }
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return result;
    }

    public String createTextViewFromValueList(List<String> valuesList, List<String> columnNames) {
        String result = setHeaders(columnNames) + "\n";
        Log.d("valuelist size ", ""+valuesList.size());
        for (int i = 0; i < valuesList.size(); i++) {
            if (valuesList.get(i) != null && valuesList.get(i).equals("NEWLINE")) {
                Log.d("valueslist ok", "createTextViewFromValueList: ");
                result += "\n";
            }
            else {
                result += valuesList.get(i) + "    ";
            }
        }

        Log.d("createtextresult: ", result);
        return result;
    }

    private boolean initStatement(String query) {
        try {
            statement = connection.createStatement();
            statement.executeQuery(query);
            Commons.SetMessage("Completted.");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            Commons.SetMessage("Error: " + e.getMessage());
            return false;
        }
    }

    private ResultSet getResultSet() {
        ResultSet rs = null;
        if (statement != null) {
            try {
                Log.d("bennvan", "getResultSet: ");
                rs = statement.getResultSet();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Log.d("getResultSet : ", "getResultSet: " + rs == null ? "true" : "false");
        return rs;
    }

    private String setHeaders(List<String> columnNames) {
        String result="";
        for (int i = 0; i < columnNames.size(); i++) {
            result += columnNames.get(i) + "    ";
        }
        return result;
    }


}
