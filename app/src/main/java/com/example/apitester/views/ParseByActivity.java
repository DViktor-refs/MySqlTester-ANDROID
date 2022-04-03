package com.example.apitester.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.apitester.R;

public class ParseByActivity extends AppCompatActivity {

    TextView tv_parseby_id;
    TextView tv_parseby_userid;
    TextView tv_parseby_title;
    TextView tv_parseby_id_rndbutton;
    TextView tv_parseby_userid_rndbutton;
    TextView tv_parseby_title_rndbutton;
    EditText et_parseby_id;
    EditText et_parseby_userid;
    EditText et_parseby_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_by);
        initalizeGUI();

        /*
        /   https://www.youtube.com/watch?v=izuldLJoOxs
         */


    }

    private void initalizeGUI() {

        tv_parseby_id = findViewById(R.id.tv_parseby_id);
        tv_parseby_userid = findViewById(R.id.tv_parseby_userid);
        tv_parseby_title = findViewById(R.id.tv_parseby_title);
        tv_parseby_id_rndbutton = findViewById(R.id.tv_parseby_id_rndtbutton);
        tv_parseby_userid_rndbutton = findViewById(R.id.tv_parseby_userid_rndtbutton);
        tv_parseby_title_rndbutton = findViewById(R.id.tv_parseby_title_rndtbutton);
        et_parseby_id = findViewById(R.id.et_parseby_id);
        et_parseby_userid = findViewById(R.id.et_parseby_userid);
        et_parseby_title = findViewById(R.id.et_parseby_title);

    }
}