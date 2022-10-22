package com.example.nanlinkdemo.mvp.view;


import com.example.nanlinkdemo.ui.MyDialog;

public interface ResetPasswordView {

    void setPresenter();

    String getEmail();

    void finish();

    String getCode();

    void updateGetCodeBtn(boolean able);

    void startLoading();

    void stopLoading();

    void showMyDialog(int type, String title, String message, String neutralText, MyDialog.NeutralOnClickListener neutralListener);

    void updateResetBtn(boolean able);

    void updateGetCodeBtnText(String text);

    int getType();
}
