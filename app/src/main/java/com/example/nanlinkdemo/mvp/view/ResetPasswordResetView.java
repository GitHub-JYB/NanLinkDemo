package com.example.nanlinkdemo.mvp.view;

import com.example.nanlinkdemo.ui.MyDialog;

public interface ResetPasswordResetView {
    String getEmail();

    String getCode();

    int getType();

    String getPassword();

    String getConfirmPassword();

    void finish();

    void showMyDialog(int type, String title, String message, String neutralText, MyDialog.NeutralOnClickListener neutralListener);

    void stopLoading();

    void startLoading();

    void dismissMyDialog();

}
