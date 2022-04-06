package com.example.apitester.views;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.example.apitester.databinding.ActivityParseBinding;
import com.example.apitester.interfaces.*;
import com.example.apitester.model_retrofit.Data;
import com.example.apitester.model_retrofit.Parse;
import java.util.List;

public class ParseActivity extends AppCompatActivity implements mvpinterfaces.Model {

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
        setfullScreen();
        setContentView(view);
        counter = 0;
        update();
        initListeners();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void setfullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @SuppressLint("ClickableViewAccessibility")
    private void initListeners() {
        viewBinding.btnParseactivityBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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