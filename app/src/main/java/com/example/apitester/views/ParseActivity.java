package com.example.apitester.views;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.apitester.interfaces.*;
import com.example.apitester.API_retrofit.API;
import com.example.apitester.R;
import com.example.apitester.model_retrofit.Data;
import com.example.apitester.API_retrofit.StartRetrofit;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ParseActivity extends AppCompatActivity implements mvpinterfaces{

    Retrofit retrofit;
    Button btn_back;
    Button btn_left;
    Button btn_right;
    TextView tv_id;
    TextView tv_userid;
    TextView tv_title;
    TextView tv_body;
    TextView tv_counter;
    int counter;
    int dataSize=0;
    Handler handler = new Handler();

    public static final int PAGING_DELAY_NORMAL = 150;
    public static final int PAGING_DELAY_SLOW = 250;
    public static final int PAGING_DELAY_FAST = 50;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse);
        counter = 0;
        initalizeGUI();
        update();
        setListeners();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setListeners() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ParseActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        btn_right.setOnTouchListener(new View.OnTouchListener() {
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

        btn_left.setOnTouchListener(new View.OnTouchListener() {
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
        parse(new RetrofitCallback() {
            @Override
            public void onSuccess(List<Data> data) {
                dataSize = data.size();
                tv_counter.setText(""+(counter+1));
                tv_id.setText(""+data.get(counter).getId());
                tv_userid.setText(""+data.get(counter).getUserId());
                tv_title.setText(""+data.get(counter).getTitle());
                tv_body.setText(""+data.get(counter).getText());
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void parse(RetrofitCallback callback) {
        retrofit = StartRetrofit.getRetrofit();
        API api = retrofit.create(API.class);
        Call<List<Data>> call = api.getData();
        call.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                if (!response.isSuccessful()) {
                    Log.d("error", "response unsuccessful");
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

    Runnable increase = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(increase, PAGING_DELAY_NORMAL);

            if(counter < dataSize-1) {
                counter++;
                Log.d("counter: ", ""+counter);
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
                Log.d("counter: ", ""+counter);
                update();
            }
        }
    };

    private void initalizeGUI() {
        btn_back = findViewById(R.id.btn_parseactivity_back);
        btn_left = findViewById(R.id.btn_left);
        btn_right = findViewById(R.id.btn_right);
        tv_id = findViewById(R.id.tv_idresult);
        tv_userid = findViewById(R.id.tv_useridresult);
        tv_title = findViewById(R.id.tv_titleresult);
        tv_body = findViewById(R.id.tv_bodyresult);
        tv_counter = findViewById(R.id.tv_counter);
    }
}