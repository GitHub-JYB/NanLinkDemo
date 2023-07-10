package com.example.NanLinkDemo.mvp.model;


import com.example.NanLinkDemo.DB.bean.User;

public interface LoginModel {

    void login(String email, String password);

    void addUser(User user);

    void queryEmail(String email);

    void updateUser(User user);

    void getLastUser();
}
