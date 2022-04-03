package com.example.apitester.API_retrofit;

import com.example.apitester.model_retrofit.Data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface API {

    @GET ("posts")
    Call<List<Data>> getData();

    @POST("posts")
    Call<Data> createPost(@Body Data data);
}
