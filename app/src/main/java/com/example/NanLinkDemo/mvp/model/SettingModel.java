package com.example.NanLinkDemo.mvp.model;

import com.example.NanLinkDemo.DB.bean.User;

public interface SettingModel {
    void getSettingList();

    void updateUser(User user);
}
