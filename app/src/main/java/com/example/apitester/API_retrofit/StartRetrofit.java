package com.example.apitester.API_retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StartRetrofit {

    private static final String baseUrl = "https://jsonplaceholder.typicode.com/";
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit() {
        if (retrofit==null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            return retrofit;
        }
        else {
            return retrofit;
        }

    }
}
