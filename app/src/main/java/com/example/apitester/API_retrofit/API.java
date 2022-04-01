package com.example.apitester.API_retrofit;

import com.example.apitester.model_retrofit.Data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {

    @GET ("posts")
    Call<List<Data>> getData();
}
