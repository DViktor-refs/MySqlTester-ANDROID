package com.example.apitester.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
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
import java.io.Serializable;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements Serializable {

    private ActivityMainBinding viewBinding;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        initViewBinding();
        initRetrofit();
        initListeners();
    }

    @Override
    public void onBackPressed() {
        showQuitAlertDialog();
    }

    private void initListeners() {

        //<editor-fold desc="clear edittext row events">

        viewBinding.ivClearId.setOnClickListener(v -> viewBinding.etId.setText(""));

        viewBinding.ivClearUserid.setOnClickListener(v -> viewBinding.etUserid.setText(""));

        viewBinding.ivClearTitle.setOnClickListener(v -> viewBinding.etTitle.setText(""));

        viewBinding.ivClearBody.setOnClickListener(v -> viewBinding.etBody.setText(""));

        //</editor-fold>

        viewBinding.btnParse.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, ParseActivity.class);
            startActivity(i);
        });

        viewBinding.btnParseby.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, ParseByActivity.class);
            startActivity(i);
        });

        viewBinding.btnPost.setOnClickListener(v -> {
            if (isEveryFiledFilled()) {

                API api = retrofit.create(API.class);
                Data data = getPostDataFromFields();
                Call<Data> call = api.createPost(data);

                call.enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {
                        if(!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Error Code: "+response.code(), Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Data responseData = response.body();
                            String toastMessage = "Succesful: " + "\n" + fillContentFromResponse(Objects.requireNonNull(responseData));
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

    private void initRetrofit() {
        retrofit = StartRetrofit.getRetrofit();
    }

    private void initViewBinding() {
        viewBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = viewBinding.getRoot();
        setContentView(view);
    }

    private void setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void showQuitAlertDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        alertBuilder.setTitle("Quit")
                .setMessage("Are you sure?")
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    onBackPressed();
                    finish();
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

}