package com.example.nanlinkdemo.mvp.model;

import com.example.nanlinkdemo.DB.bean.User;

public interface UserSettingModel {
    void getSettingList();

    void updateUser(User user);
}
