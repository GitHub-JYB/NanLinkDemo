package com.example.nanlinkdemo.mvp.model;

import com.example.nanlinkdemo.DB.bean.User;

public interface SettingModel {
    void getSettingList();

    void updateUser(User user);
}
