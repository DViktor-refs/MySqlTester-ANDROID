package com.example.apitester.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.example.apitester.API_retrofit.API;
import com.example.apitester.API_retrofit.StartRetrofit;
import com.example.apitester.databinding.ActivityMainBinding;
import com.example.apitester.model_retrofit.Data;
import com.example.apitester.externalLibs.ObjectSerializer;
import com.example.apitester.utils.ViewUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements Serializable {

    private static final String SHAREDPREFNAME = "mainActivitySharedPrefs" ;
    private static final String SHAREDPREFPOSTNAME = "postFields" ;
    private static final String DATALISTNAME = "dataList";
    ActivityMainBinding viewBinding;
    Retrofit retrofit;
    ViewUtils viewUtils = new ViewUtils();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = viewBinding.getRoot();
        setFullScreen();
        setContentView(view);
        retrofit = StartRetrofit.getRetrofit();
        initListeners();
    }

    private void setFullScreen() {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onBackPressed() {
        showQuitAlertDialog();
    }

    private void initListeners() {

        viewBinding.tvMainTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SplashScreen.class);
                startActivity(i);
            }
        });

        //<editor-fold desc="clear edittext row events">
        viewBinding.ivClearId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewBinding.etId.setText("");
            }
        });

        viewBinding.ivClearUserid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewBinding.etUserid.setText("");
            }
        });

        viewBinding.ivClearTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewBinding.etTitle.setText("");
            }
        });

        viewBinding.ivClearBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewBinding.etBody.setText("");
            }
        });
        //</editor-fold>

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
                    Data data = getPostDataFromFields();
                    Call<Data> call = api.createPost(data);
                    call.enqueue(new Callback<Data>() {
                        @Override
                        public void onResponse(Call<Data> call, Response<Data> response) {
                            if(!response.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Code: "+response.code(), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Data responseData = response.body();
                                String toastMessage = "Code: " + response.code() + "\n" +
                                        fillContentFromResponse(Objects.requireNonNull(responseData));
                                Log.d("response", toastMessage);
                                Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
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

    private Data getPostDataFromFields() {
        int userId = Integer.parseInt(viewBinding.etUserid.getText().toString());
        String title = viewBinding.etTitle.getText().toString();
        String text = viewBinding.etBody.getText().toString();
        return new Data(userId, title, text);
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
        return !viewBinding.etId.getText().toString().isEmpty()
                && !viewBinding.etUserid.getText().toString().isEmpty()
                && !viewBinding.etTitle.getText().toString().isEmpty()
                && !viewBinding.etBody.getText().toString().isEmpty();
    }

    private void saveSharedPrefs(List<Data> dataList){
        SharedPreferences dataPrefs = getSharedPreferences(SHAREDPREFNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = dataPrefs.edit();
        try {
            editor.putString(DATALISTNAME, ObjectSerializer.serialize(dataList));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.apply();
    }

    private List<Data> loadSharedPrefs() {
        List<Data> result = null;
        SharedPreferences dataPrefs = getSharedPreferences(SHAREDPREFNAME, Context.MODE_PRIVATE);
        try {
            result = (ArrayList) ObjectSerializer.deserialize(dataPrefs.getString(DATALISTNAME,
                    ObjectSerializer.serialize(new ArrayList())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void showQuitAlertDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        alertBuilder.setTitle("Quit")
                .setMessage("Are you sure?")
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBackPressed();
                        finish();
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

}