package com.example.nanlinkdemo.mvp.model;


import com.example.nanlinkdemo.DB.bean.User;

public interface LoginModel {

    void login(String email, String password);

    void addUser(User user);

    void queryEmail(String email);

    void updateUser(User user);

    void getLastUser();
}
