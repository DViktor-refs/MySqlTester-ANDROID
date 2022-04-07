package com.example.apitester.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.example.apitester.databinding.ActivityParseByBinding;
import com.example.apitester.interfaces.mvpinterfaces;
import com.example.apitester.model_retrofit.Data;
import com.example.apitester.model_retrofit.Parse;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ParseByActivity extends AppCompatActivity implements mvpinterfaces.Model{

    ActivityParseByBinding viewBinding;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityParseByBinding.inflate(getLayoutInflater());
        view = viewBinding.getRoot();
        setFullScreen();
        setContentView(view);
        initListeners();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void initListeners() {
        Random rnd = new Random();

        //<editor-fold desc="clear edittext row events">
        viewBinding.ivClearIdParseby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewBinding.etParsebyId.setText("");
            }
        });

        viewBinding.ivClearUseridParseby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewBinding.etParsebyUserid.setText("");
            }
        });

        viewBinding.ivClearTitleParseby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewBinding.etParsebyTitle.setText("");
            }
        });
        //</editor-fold>

        viewBinding.tvParsebyIdRndtbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Parse p = new Parse();
                p.parse(getApplicationContext(), new RetrofitCallback() {
                    @Override
                    public void onSuccess(List<Data> data) {
                        if (data.size() > 0) {
                            int random = rnd.nextInt(data.size());
                            viewBinding.etParsebyId.setText(String.valueOf(data.get(random).getId()));
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        Toast.makeText(getApplicationContext(), "ERROR" + message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        viewBinding.tvParsebyUseridRndtbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Parse p = new Parse();
                p.parse(getApplicationContext(), new RetrofitCallback() {
                    @Override
                    public void onSuccess(List<Data> data) {
                        if (data.size() > 0) {
                            int random = rnd.nextInt(data.size());
                            viewBinding.etParsebyUserid.setText(String.valueOf(data.get(random).getUserId()));
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        Toast.makeText(getApplicationContext(), "ERROR" + message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        viewBinding.tvParsebyTitleRndtbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Parse p = new Parse();
                p.parse(getApplicationContext(), new RetrofitCallback() {
                    @Override
                    public void onSuccess(List<Data> data) {
                        if (data.size() > 0) {
                            int random = rnd.nextInt(data.size());
                            viewBinding.etParsebyTitle.setText(String.valueOf(data.get(random).getTitle()));
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        Toast.makeText(getApplicationContext(), "ERROR" + message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        viewBinding.btnParsebyback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewBinding.btnParseby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Parse p = new Parse();
                p.parseBy(fillParamList(), getApplicationContext(), new RetrofitCallback() {
                    @Override
                    public void onSuccess(List<Data> data) {
                        Log.d("result data length: ", ""+data.size());
                        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, data);
                        viewBinding.scrollviewParseby.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(String message) {
                        Toast.makeText(getApplicationContext(), "ERROR: " + message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private List<String> fillParamList() {

        String id=""+viewBinding.etParsebyId.getText().toString();
        if (!isNumeric(id)) {
            id = "";
        }

        String userId=""+viewBinding.etParsebyUserid.getText().toString();
        if (!isNumeric(userId)) {
            userId = "";
        }

        String title=""+viewBinding.etParsebyTitle.getText().toString();

        return Arrays.asList(id, userId, title, "", "" );
    }

    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}

