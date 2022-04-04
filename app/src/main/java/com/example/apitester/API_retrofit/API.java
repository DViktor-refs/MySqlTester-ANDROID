package com.example.apitester.API_retrofit;

import com.example.apitester.model_retrofit.Data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface API {

    @GET ("posts")
    Call<List<Data>> getData();

    @GET ("posts")
    Call<List<Data>> getDataBy(@Query("id") Integer idParam,
                         @Query("userId") Integer userIdParam,
                         @Query("title") String titleParam,
                         @Query("_sort") String sort,
                         @Query("_order") String order);

    @POST("posts")
    Call<Data> createPost(@Body Data data);
}
