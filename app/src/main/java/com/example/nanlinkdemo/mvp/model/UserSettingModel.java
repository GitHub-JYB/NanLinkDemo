package com.example.nanlinkdemo.mvp.model;

import com.example.nanlinkdemo.DB.bean.User;

public interface UserSettingModel {
    void getSettingList(User user);

    void getLastUser();

    void getOnlineUser();

    void updateUser(User user);
}
