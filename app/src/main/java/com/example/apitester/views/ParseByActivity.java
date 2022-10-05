package com.example.apitester.views;

import androidx.appcompat.app.AppCompatActivity;
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

    private ActivityParseByBinding viewBinding;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        initViewBinding();
        initListeners();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void initViewBinding() {
        viewBinding = ActivityParseByBinding.inflate(getLayoutInflater());
        view = viewBinding.getRoot();
        setContentView(view);
    }

    private void setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void initListeners() {
        Random rnd = new Random();

        //<editor-fold desc="clear edittext row events">
        viewBinding.ivClearIdParseby.setOnClickListener(v -> viewBinding.etParsebyId.setText(""));

        viewBinding.ivClearUseridParseby.setOnClickListener(v -> viewBinding.etParsebyUserid.setText(""));

        viewBinding.ivClearTitleParseby.setOnClickListener(v -> viewBinding.etParsebyTitle.setText(""));
        //</editor-fold>

        //<editor-fold desc="Random button listeners">
        viewBinding.tvParsebyIdRndtbutton.setOnClickListener(v -> {
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
        });

        viewBinding.tvParsebyUseridRndtbutton.setOnClickListener(v -> {
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
        });

        viewBinding.tvParsebyTitleRndtbutton.setOnClickListener(v -> {
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
        });
        //</editor-fold>

        //<editor-fold desc="Button listeners">
        viewBinding.btnParsebyback.setOnClickListener(v -> finish());

        viewBinding.btnParseby.setOnClickListener(v -> {
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
        });
        //</editor-fold>
    }

    private List<String> fillParamList() {

        String id = "" + viewBinding.etParsebyId.getText().toString();
        if (!isNumeric(id)) {
            id = "";
        }

        String userId = "" + viewBinding.etParsebyUserid.getText().toString();
        if (!isNumeric(userId)) {
            userId = "";
        }

        String title = "" + viewBinding.etParsebyTitle.getText().toString();

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

