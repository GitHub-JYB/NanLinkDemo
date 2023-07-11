package com.example.NanLinkDemo.mvp.presenter;

import android.view.View;

import com.example.NanLinkDemo.bean.Message;

public interface ResetPasswordResetPresenter {
    void switchOnclick(View v);

    void sendMesToView(Message message);

    void showWarnToView();
}
