package com.example.apitester.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apitester.API_retrofit.API;
import com.example.apitester.API_retrofit.StartRetrofit;
import com.example.apitester.R;
import com.example.apitester.model_retrofit.Data;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity  {

    Button btn_parse;
    Button btn_post;
    EditText et_id;
    EditText et_userid;
    EditText et_title;
    EditText et_body;
    Retrofit retrofit;
    Button btn_test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initalizeGUI();

        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(i);
            }
        });

        btn_parse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ParseActivity.class);
                startActivity(i);
            }
        });

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEveryFiledFilled()) {
                    API api = retrofit.create(API.class);
                    Data data = (new Data(Integer.parseInt(et_userid.getText().toString()),
                            et_title.getText().toString(),
                            et_body.getText().toString()));
                    Call<Data> call = api.createPost(data);
                    call.enqueue(new Callback<Data>() {
                        @Override
                        public void onResponse(Call<Data> call, Response<Data> response) {
                            if(!response.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), ""+response.code(), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Data responseData = response.body();
                                String content = "";
                                content += "ID: "+responseData.getId() + "\n";
                                content += "USERID: "+responseData.getUserId() + "\n";
                                content += "TITLE: "+responseData.getTitle() + "\n";
                                content += "BODY: "+responseData.getText() + "\n";
                                Log.d("response", content);
                                Toast.makeText(getApplicationContext(), ""+response.code(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Data> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "ERROR: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), "Please fill every fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        
    }

    private boolean isEveryFiledFilled() {
        if (!et_id.getText().toString().isEmpty()
            && !et_userid.getText().toString().isEmpty()
            && !et_title.getText().toString().isEmpty()
            && !et_body.getText().toString().isEmpty()) {
            return true;
        }
        else {
            return false;
        }
    }

    private void createPost(Data data) {




    }

    private void initalizeGUI() {
        btn_parse = findViewById(R.id.btn_parse);
        btn_post = findViewById(R.id.btn_post);
        et_id = findViewById(R.id.et_id);
        et_userid = findViewById(R.id.et_userid);
        et_title = findViewById(R.id.et_title);
        et_body = findViewById(R.id.et_body);
        retrofit = StartRetrofit.getRetrofit();
        btn_test = findViewById(R.id.btn_test);
    }
}