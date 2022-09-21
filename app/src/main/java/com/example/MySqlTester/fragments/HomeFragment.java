package com.example.MySqlTester.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.MySqlTester.mySqlStatements.EncDec;
import com.example.MySqlTester.handlers.ConnectionHelper;
import com.example.MySqlTester.R;
import com.example.MySqlTester.mySqlStatements.Commons;
import java.sql.Connection;

public class HomeFragment extends Fragment {

    Button runButton;
    View view;
    EditText et_hostName;
    EditText et_port;
    EditText et_databasename;
    EditText et_user;
    EditText et_pass;
    TextView status;
    ConnectionHelper connectionHelper = new ConnectionHelper();
    Connection connection = null;
    String host;
    String port;
    String databasename;
    String user;
    String pass;

    public HomeFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.homelayout, container, false);
        }

        initialize();

        runButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                readInputFields();
                connectionHelper.setStatusTextView(status);
                connection = connectionHelper.connection(host, port, databasename, user, pass);

                if (connection != null) {
                    encodeSharedPrefs();
                    writeSharedPrefs();
                }
                else {
                    connection = null;
                }

            }
        });

        return view;
    }

    private void initialize() {
        setGui();
        setStatus();
    }

    private void setStatus() {
        status.setText(Commons.setStatus());
    }

    private void encodeSharedPrefs() {
        host = EncDec.encode(host);
        port = EncDec.encode(port);
        databasename = EncDec.encode(databasename);
        user = EncDec.encode(user);
        pass = EncDec.encode(pass);
    }

    private void writeSharedPrefs() {
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("host", host);
        editor.putString("port", port);
        editor.putString("dbname", databasename);
        editor.putString("user", user);
        editor.putString("pass", pass);
        editor.apply();
    }

    private void readInputFields() {
        host = et_hostName.getText().toString();
        port = et_port.getText().toString();
        databasename = et_databasename.getText().toString();
        user = et_user.getText().toString();
        pass = et_pass.getText().toString();
    }

    private void setGui() {
        runButton =view.findViewById(R.id.select_btn_run);
        et_hostName = view.findViewById(R.id.select_et_name);
        et_port = view.findViewById(R.id.home_et_portname);
        et_databasename = view.findViewById(R.id.home_et_dbname);
        et_user = view.findViewById(R.id.home_et_username);
        et_pass = view.findViewById(R.id.home_et_password);
        status = view.findViewById(R.id.select_tv_status);
    }


}