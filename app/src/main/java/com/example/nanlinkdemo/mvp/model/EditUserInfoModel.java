package com.example.nanlinkdemo.mvp.model;

import com.example.nanlinkdemo.DB.bean.User;
import com.example.nanlinkdemo.bean.EditUser;

public interface EditUserInfoModel {
    void editInfo(String token, EditUser user);

    void updateUser(User user);
}
