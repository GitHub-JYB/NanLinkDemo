package com.example.NanLinkDemo.util;

import com.example.NanLinkDemo.bean.DeviceMessage;
import com.example.NanLinkDemo.bean.EditUser;
import com.example.NanLinkDemo.bean.Feedback;
import com.example.NanLinkDemo.bean.LoginUser;
import com.example.NanLinkDemo.bean.Message;
import com.example.NanLinkDemo.bean.RegisterUser;
import com.example.NanLinkDemo.bean.ResetPasswordUser;


import io.reactivex.Single;
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
        Single<String> getDeviceData(@Query("deviceId") String deviceId);
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
