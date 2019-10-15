package com.mhdhussein.aralpackagemanagement.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AralClient {

//    public static final String BASE_URL  = "http://139.180.130.163/"; //temp
    public static final String BASE_URL  = "http://aralsoft.net/amas/"; //temp

    private static Retrofit retrofit  = null;

    public static Retrofit getClient(){



        Gson gson = new GsonBuilder()
                .setLenient()
                .setPrettyPrinting()
                .enableComplexMapKeySerialization()
                .generateNonExecutableJson()
                .create();



        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }

        return retrofit;
    }

    public static ApiService getApiService(){

        return getClient().create(ApiService.class);
    }
}
