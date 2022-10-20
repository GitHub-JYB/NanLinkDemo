package com.example.nanlinkdemo.mvp.model;


public interface RegisterModel {

    void register(String email, String password, String code, String nickName);

    void getCode(String email, int code_register);

    void startCountDown();
}
