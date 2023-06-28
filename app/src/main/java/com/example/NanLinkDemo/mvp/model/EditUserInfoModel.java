package com.example.NanLinkDemo.mvp.model;

import com.example.NanLinkDemo.DB.bean.User;
import com.example.NanLinkDemo.bean.EditUser;

public interface EditUserInfoModel {
    void editInfo(String token, EditUser user);

    void updateUser(User user);
}
