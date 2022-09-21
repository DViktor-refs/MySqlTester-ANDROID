package com.example.MySqlTester.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.MySqlTester.R;
import com.example.MySqlTester.mySqlStatements.Commons;
import com.example.MySqlTester.mySqlStatements.DropTable;

public class DropTableFragment extends Fragment {

    View view;
    TextView status;
    EditText tableName;
    Button runButton;
    DropTable drop = new DropTable();


    public DropTableFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.droptablelayout, container, false);
        }

        initalize();

        runButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drop.dropTable(tableName.getText().toString());
                updateStatusField();
            }
        });
        return view;
    }

    private void updateStatusField() {
        status.setText(Commons.getMessage());
    }

    private void initalize() {
        setGui();
        setStatus();
    }

    private void setGui() {
        status = view.findViewById(R.id.select_tv_status);
        tableName = view.findViewById(R.id.select_et_name);
        runButton = view.findViewById(R.id.select_btn_run);
    }

    private void setStatus() {
        status.setText(Commons.setStatus());
    }
}