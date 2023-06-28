package com.example.nanlinkdemo.mvp.view;


import com.example.nanlinkdemo.ui.MyDialog;

public interface LoginView {

    void initForgetPassword();

    void setPresenter();

    void setCheckImage(int resId);

    String getEmail();

    String getPassword();

    void finish();

    void showMyDialog(int type, String title, String message, String neutralText, MyDialog.NeutralOnClickListener neutralListener);

    void stopLoading();

    void startLoading();

    void updateEmail(String email);
}
