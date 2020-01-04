package com.agilya.syc.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit retrofit;
//    private static final String BASE_URL = "http://demo1144374.mockable.io/";
    //private static final String BASE_URL = "https://api.nytimes.com/svc/topstories/v2/";
    private static final String BASE_URL = "https://api.nytimes.com/";

    public static Retrofit getRetrofitInstance(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        }
        return retrofit;
    }
}
