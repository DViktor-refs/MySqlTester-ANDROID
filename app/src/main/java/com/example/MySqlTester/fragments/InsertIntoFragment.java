package com.example.MySqlTester.fragments;

import static com.example.MySqlTester.mySqlStatements.Commons.isConnected;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.MySqlTester.R;
import com.example.MySqlTester.adapters.InsertIntoLayoutArrayAdapter;
import com.example.MySqlTester.adapters.SelectLayoutItemModel;
import com.example.MySqlTester.handlers.ConnectionHelper;
import com.example.MySqlTester.mySqlStatements.Commons;
import com.example.MySqlTester.mySqlStatements.InsertInto;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InsertIntoFragment extends Fragment {

    TextView status;
    View view;
    Connection connection;
    TextView result;
    EditText tableName;
    Button runButton,searchButton;
    RecyclerView recyclerView;
    InsertIntoLayoutArrayAdapter adapter;
    DatabaseMetaData dbmd = null;
    String[] types = {"TABLE"};
    InsertInto insertInto = new InsertInto();

    public InsertIntoFragment() { }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.insertintolayout, container, false);
        }

        initialize();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = tableName.getText().toString();

                if (isTableNameValid(getTableNames(), name)) {
                    setViewIfTableNameValid();
                }
                else {
                    setViewIfTableNameInvalid();
                }

            }
        });

        runButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected()) {
                    if (!adapter.getValuesOfCheckedItems().isEmpty()) {
                        insertInto.executeStatement(tableName.getText().toString(),
                                adapter.getValuesOfCheckedItems(),
                                adapter.getDataListValues());
                        status.setText(Commons.getMessage());
                    }
                    else{
                        Commons.SetMessage("Please, check at least one column name and add some value.");
                    }
                }
                else {
                    Commons.SetMessage("Disconnected.");
                }
                status.setText(Commons.getMessage());
            }
        });

        return view;
    }

    private void setViewIfTableNameInvalid() {
        resetRecyclerView();
        runButton.setEnabled(false);
        status.setText("Tablename filed doesn't exists of empty.");
    }

    private void setViewIfTableNameValid() {
        List<String> columnNames = getColumnNames(tableName);
        setAdapter(columnNames);
        status.setText("Tablename found in database.");
        runButton.setEnabled(true);
    }

    private void setAdapter(List<String> columnNames) {
        adapter = new InsertIntoLayoutArrayAdapter(setUpItemModelList(columnNames));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void resetRecyclerView() {
        recyclerView.setAdapter(null);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private List<String> getColumnNames(EditText tableName) {
        String name= tableName.getText().toString();
        List<String> result = new ArrayList<>();

        if (connection != null && !name.isEmpty()) {
            try {
                DatabaseMetaData dbmd = connection.getMetaData();
                ResultSet rset = dbmd.getColumns(null, null, name, null);
                while (rset.next()) {
                    result.add(rset.getString(4));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                status.setText("Error: ." + e.getMessage());
            }
        }
        else {
            status.setText("Disconnected.");
        }
        return result;
    }

    private boolean isTableNameValid(List<String> tableNames, String name) {
        boolean result = false;

        if (name != null) {
            for (int i = 0; i < tableNames.size(); i++) {
                if (tableNames.get(i).equals(name)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    private List<String> getTableNames() {

        List<String> result = new ArrayList<>();
        if(isConnected()) {
            try {
                dbmd = ConnectionHelper.getDatabaseMetaData();
                ResultSet rs = dbmd.getTables(null, null, "%", types);
                while (rs.next()) {
                    result.add(rs.getString("TABLE_NAME"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                status.setText("Error: " + e.getMessage());
            }
        }
        status.setText("Disconnected.");
        return result;
    }

    private ArrayList<SelectLayoutItemModel> setUpItemModelList(List<String> columns) {
        ArrayList<SelectLayoutItemModel> result = new ArrayList<>();
        for (int i = 0; i < columns.size(); i++) {
            result.add(new SelectLayoutItemModel(columns.get(i)));
        }
        return result;
    }

    private void initialize() {
        setGui();
        connection = ConnectionHelper.getConnection();
        setStatus();

    }

    private void setGui() {
        tableName = view.findViewById(R.id.insert_et_tablename);
        runButton = view.findViewById(R.id.insert_btn_run);
        searchButton = view.findViewById(R.id.insert_btn_search);
        recyclerView = view.findViewById(R.id.insert_recyclerView);
        status = view.findViewById(R.id.insert_tv_status);
        result = view.findViewById(R.id.insert_tv_result);
        runButton.setEnabled(false);
    }

    private void setStatus() {
        status.setText(Commons.setStatus());
    }

}