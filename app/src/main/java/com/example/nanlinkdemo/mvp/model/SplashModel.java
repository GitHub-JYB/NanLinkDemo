package com.example.nanlinkdemo.mvp.model;

import com.example.nanlinkdemo.DB.bean.User;

public interface SplashModel {
    void startCountDown();

    void getOnlineUser();

    void getUserInfo(String token);

    void updateUser(User user);

    void getLastUser();

    void getDeviceList();
}
