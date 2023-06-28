package com.example.NanLinkDemo.mvp.model;

import com.example.NanLinkDemo.DB.bean.User;

public interface UserSettingModel {
    void getSettingList();

    void updateUser(User user);
}
