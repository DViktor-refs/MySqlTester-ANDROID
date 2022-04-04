package com.example.apitester.views;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.example.apitester.API_retrofit.API;
import com.example.apitester.API_retrofit.StartRetrofit;
import com.example.apitester.databinding.ActivityMainBinding;
import com.example.apitester.model_retrofit.Data;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity  {

    ActivityMainBinding viewBinding;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = viewBinding.getRoot();
        setContentView(view);
        retrofit = StartRetrofit.getRetrofit();
        initListeners();
    }

    private void initListeners() {

        viewBinding.btnParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ParseActivity.class);
                startActivity(i);
            }
        });

        viewBinding.btnParseby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ParseByActivity.class);
                startActivity(i);
            }
        });

        viewBinding.btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEveryFiledFilled()) {
                    API api = retrofit.create(API.class);
                    Data data = (new Data(Integer.parseInt(viewBinding.etUserid.getText().toString()),
                            viewBinding.etTitle.getText().toString(),
                            viewBinding.etBody.getText().toString()));
                    Call<Data> call = api.createPost(data);
                    call.enqueue(new Callback<Data>() {
                        @Override
                        public void onResponse(Call<Data> call, Response<Data> response) {
                            if(!response.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), ""+response.code(), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Data responseData = response.body();
                                Log.d("response", fillContentFromResponse(responseData));
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

    private String fillContentFromResponse(Data responseData) {
        String content = "";
        content += "ID: "+responseData.getId() + "\n";
        content += "USERID: "+responseData.getUserId() + "\n";
        content += "TITLE: "+responseData.getTitle() + "\n";
        content += "BODY: "+responseData.getText() + "\n";
        return content;
    }

    private boolean isEveryFiledFilled() {
        if (!viewBinding.etId.getText().toString().isEmpty()
            && !viewBinding.etUserid.getText().toString().isEmpty()
            && !viewBinding.etTitle.getText().toString().isEmpty()
            && !viewBinding.etBody.getText().toString().isEmpty()) {
            return true;
        }
        else {
            return false;
        }
    }

    private void createPost(Data data) {

    }

}