package com.example.apitester.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.apitester.R;
import com.example.apitester.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity {

    ActivityMain2Binding activityMain2Binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main2);
        activityMain2Binding = ActivityMain2Binding.inflate(getLayoutInflater());
        View view = activityMain2Binding.getRoot();
        setContentView(view);

        activityMain2Binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "fuckyeah", Toast.LENGTH_SHORT).show();
            }
        });

        





    }
}