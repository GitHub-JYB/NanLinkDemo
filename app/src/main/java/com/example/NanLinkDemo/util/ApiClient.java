package com.example.NanLinkDemo.util;

import com.example.NanLinkDemo.DB.bean.DeviceData;
import com.example.NanLinkDemo.JsonTypeAdapter.StringDefault0Adapter;
import com.example.NanLinkDemo.bean.DeviceDataMessage;
import com.example.NanLinkDemo.bean.DeviceMessage;
import com.example.NanLinkDemo.bean.EditUser;
import com.example.NanLinkDemo.bean.Feedback;
import com.example.NanLinkDemo.bean.LoginUser;
import com.example.NanLinkDemo.bean.Message;
import com.example.NanLinkDemo.bean.RegisterUser;
import com.example.NanLinkDemo.bean.ResetPasswordUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;


public class ApiClient {

    public static final String BASE_URL = "https://serv-test.nanlink.com";
    public static final String PRIVACY_POLICY_URL = "https://cdn-test.nanlink.com/nanlink_privacy_policy_zh.html";
    public static final String USER_AGREEMENT_URL = "https://cdn-test.nanlink.com/nanlink_user_agreement_zh.html";
    public static final String ABOUT_URL = "https://cdn-test.nanlink.com/nanlink_about_zh.html";
    public static final String Function_Register = "register";
    public static final String Function_GetCode = "getCode";
    public static final String Function_VerifyCode = "verifyCode";
    private static final int CONN_TIME_OUT = 5*1000;
    private static final int READ_TIME_OUT = 5*1000;


    private static Retrofit retrofit = null;

    public interface ApiService{


        @POST("/nanlinkUser/v1/user/loginAccount")
        Single<Message> login(@Body LoginUser loginUser);

        @POST("/nanlinkUser/v1/user/registerAccount")
        Single<Message> register(@Body RegisterUser registerUser);

        @GET("/nanlinkUser/v1/user/emailCode")
        Single<Message> getCode(@Query("email") String email, @Query("type") int type);

        @GET("/nanlinkUser/v1/user/verifyCode")
        Single<Message> verifyCode(@Query("email") String email, @Query("code") String code);

        @POST("/nanlinkUser/v1/user/resetPwd")
        Single<Message> resetPassword(@Body ResetPasswordUser resetPasswordUser);

        @GET("/nanlinkUser/v1/user/getInfo")
        Single<Message> getUserInfo(@Header("token") String token);

        @POST("/nanlinkUser/v1/user/editInfo")
        Single<Message> editUserInfo(@Header("token") String token, @Body EditUser editUser);

        @POST("/nanlinkUser/v1/feedback/submit")
        Single<Message> submitFeedback(@Header("token") String token, @Body Feedback feedback);

        @GET("/nanlinkDevice/v1/device/listDevice")
        Single<DeviceMessage> getDeviceLIst();

        @GET("/nanlinkDevice/v1/device/queryConfig")
        Single<DeviceDataMessage> getDeviceData(@Query("deviceId") String deviceId, @Query("contentVersion") String contentVersion);
    }

    private static Retrofit getClient(String url){
        if (url.equals(BASE_URL)){
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(getOkHttpClient())
                        .addConverterFactory(GsonConverterFactory.create(buildGson()))
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
        }
        return retrofit;
    }

    public static ApiService getService(String url){
        return getClient(url).create(ApiService.class);
    }

    private static OkHttpClient getOkHttpClient(){

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //超时设置
        builder.connectTimeout(CONN_TIME_OUT, TimeUnit.MILLISECONDS);
        builder.readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS);

        return builder.build();
    }

    public static Gson buildGson() {
        Gson gson = new GsonBuilder().serializeNulls()
                    .registerTypeAdapter(String.class, new StringDefault0Adapter())
                    .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
                    .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
                    .registerTypeAdapter(Long.class, new LongDefault0Adapter())
                    .registerTypeAdapter(long.class, new LongDefault0Adapter())
                    .create();
        return gson;
    }

}
