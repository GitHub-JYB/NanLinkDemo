package com.example.NanLinkDemo.mvp.model;

public interface ForgetPasswordModel {
    void getCode(String email, int code_type);

    void verifyCode(String email, String code);

    void startCountDown();

}
