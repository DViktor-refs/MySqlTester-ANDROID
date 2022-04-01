package com.example.apitester.interfaces;

import com.example.apitester.model_retrofit.Data;

import java.util.List;

public interface mvpinterfaces {

    public interface RetrofitCallback {
        void onSuccess(List<Data> data);
        void onFailure(String message);
    }
}
