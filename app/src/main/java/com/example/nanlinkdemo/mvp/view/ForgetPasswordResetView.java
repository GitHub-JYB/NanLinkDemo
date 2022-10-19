package com.example.nanlinkdemo.mvp.view;

import com.example.nanlinkdemo.ui.MyDialog;

public interface ForgetPasswordResetView {
    String getEmail();

    String getCode();

    String getPassword();

    String getConfirmPassword();

    void finish();

    void showMistakeDialog(String title, String content, int type);

    void stopLoading();

    void startLoading();

    void dismissDialog();

    void showSuccessDialog(String title, String content, int type, MyDialog.NeutralOnClickListener listener);
}
