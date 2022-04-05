package com.example.apitester.views;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.apitester.databinding.ActivityParseBinding;
import com.example.apitester.interfaces.*;
import com.example.apitester.API_retrofit.API;
import com.example.apitester.R;
import com.example.apitester.model_retrofit.Data;
import com.example.apitester.API_retrofit.StartRetrofit;
import com.example.apitester.model_retrofit.Parse;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ParseActivity extends AppCompatActivity implements mvpinterfaces.Model {

    Retrofit retrofit;

    ActivityParseBinding viewBinding;
    int counter;
    int dataSize=0;
    Handler handler = new Handler();
    View view;

    public static final int PAGING_DELAY_NORMAL = 150;
    public static final int PAGING_DELAY_SLOW = 250;
    public static final int PAGING_DELAY_FAST = 50;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityParseBinding.inflate(getLayoutInflater());
        view = viewBinding.getRoot();
        setContentView(view);
        counter = 0;
        update();
        initListeners();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initListeners() {
        viewBinding.btnParseactivityBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ParseActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        viewBinding.btnRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    increase.run();
                }
                else if ((event.getAction() == MotionEvent.ACTION_CANCEL) || (event.getAction() == MotionEvent.ACTION_UP)) {
                    handler.removeCallbacks(increase);
                }
                return false;
            }
        });

        viewBinding.btnLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    decrease.run();
                }
                else if ((event.getAction() == MotionEvent.ACTION_CANCEL) || (event.getAction() == MotionEvent.ACTION_UP)) {
                    handler.removeCallbacks(decrease);
                }
                return false;
            }
        });
    }

    private void update() {
        Parse p = new Parse();
        p.parse(getApplicationContext(), new RetrofitCallback() {
            @Override
            public void onSuccess(List<Data> data) {
                dataSize = data.size();
                setTextViewsFromData(data);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setTextViewsFromData(List<Data> data) {
        viewBinding.tvCounter.setText(""+(counter+1));
        viewBinding.tvIdresult.setText(""+data.get(counter).getId());
        viewBinding.tvUseridresult.setText(""+data.get(counter).getUserId());
        viewBinding.tvTitleresult.setText(""+data.get(counter).getTitle());
        viewBinding.tvBodyresult.setText(""+data.get(counter).getText());
    }

    /*

    private void parse(RetrofitCallback callback) {
        retrofit = StartRetrofit.getRetrofit();
        API api = retrofit.create(API.class);
        Call<List<Data>> call = api.getData();
        call.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "ERRORCODE: "+response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Data> data = response.body();
                callback.onSuccess(data);
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {
                callback.onFailure("Error: "+t.getMessage());
            }
        });
    }

    */

    Runnable increase = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(increase, PAGING_DELAY_NORMAL);

            if(counter < dataSize-1) {
                counter++;
                update();
            }
        }
    };

    Runnable decrease = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(decrease, PAGING_DELAY_NORMAL);

            if(counter > 0) {
                counter--;
                update();
            }
        }
    };
}