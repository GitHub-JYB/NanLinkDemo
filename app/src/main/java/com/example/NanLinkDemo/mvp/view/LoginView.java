package com.example.NanLinkDemo.mvp.view;


import com.example.NanLinkDemo.ui.MyDialog;

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
