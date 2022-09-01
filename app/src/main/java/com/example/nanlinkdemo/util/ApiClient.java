package com.example.nanlinkdemo.util;

import com.example.nanlinkdemo.bean.LoginUser;
import com.example.nanlinkdemo.bean.Message;
import com.example.nanlinkdemo.bean.RegisterUser;


import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public class ApiClient {

    public static final String BASE_URL = "https://serv-test.nanlink.com";


    private static Retrofit retrofit = null;

    public interface ApiService{


        @POST("/nanlinkUser/v1/user/loginAccount")
        Observable<Message> login(@Body LoginUser loginUser, @Query("token") String token);

        @POST("/nanlinkUser/v1/user/registerAccount")
        Observable<Message> register(@Body RegisterUser registerUser);

    }

    private static Retrofit getClient(String url){
        if (url.equals(BASE_URL)){
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
        }
        return retrofit;
    }

    public static ApiService getService(String url){
        return getClient(url).create(ApiService.class);
    }

}
