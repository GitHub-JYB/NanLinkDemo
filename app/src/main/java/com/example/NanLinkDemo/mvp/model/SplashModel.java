package com.example.NanLinkDemo.mvp.model;

import com.example.NanLinkDemo.DB.bean.User;

public interface SplashModel {
    void startCountDown();

    void getOnlineUser();

    void getUserInfo(String token);

    void updateUser(User user);

    void getDeviceList();
}
