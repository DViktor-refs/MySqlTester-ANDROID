package com.example.apitester.model_retrofit;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.apitester.API_retrofit.API;
import com.example.apitester.API_retrofit.StartRetrofit;
import com.example.apitester.interfaces.mvpinterfaces;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Parse {

    private Retrofit retrofit;
    Context context;

    public void parse(Context context, mvpinterfaces.Model.RetrofitCallback callback) {
        this.context = context;
        retrofit = StartRetrofit.getRetrofit();
        API api = retrofit.create(API.class);
        Call<List<Data>> call = api.getData();
        call.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context.getApplicationContext(), "ERRORCODE: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Data> data = response.body();
                callback.onSuccess(data);
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {
                callback.onFailure("Error: " + t.getMessage());
            }
        });
    }

    public void parseBy(List<String> params, Context context, mvpinterfaces.Model.RetrofitCallback callback) {
        this.context = context;
        retrofit = StartRetrofit.getRetrofit();
        API api = retrofit.create(API.class);
        Call<List<Data>> call = api.getDataBy(params.get(0).isEmpty() ? null : Integer.parseInt(params.get(0)),
                params.get(1).isEmpty() ? null : Integer.parseInt(params.get(1)),
                params.get(2).isEmpty() ? null : params.get(2),
                null,
                null);

        call.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context.getApplicationContext(), "ERRORCODE: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {

            }
        });
    }




}




