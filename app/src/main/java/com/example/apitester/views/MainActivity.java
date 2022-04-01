package com.example.apitester.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.apitester.R;

public class MainActivity extends AppCompatActivity {

    Button btn_parse;
    Button btn_post;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initalizeGUI();

        btn_parse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ParseActivity.class);
                startActivity(i);
            }
        });
    }

    private void initalizeGUI() {
        btn_parse = findViewById(R.id.btn_parse);
        btn_post = findViewById(R.id.btn_post);
    }
}