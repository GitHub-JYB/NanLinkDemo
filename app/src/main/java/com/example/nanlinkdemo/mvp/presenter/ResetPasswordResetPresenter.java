package com.example.nanlinkdemo.mvp.presenter;

import android.view.View;

import com.example.nanlinkdemo.bean.Message;

public interface ResetPasswordResetPresenter {
    void switchOnclick(View v);

    void sendMesToView(Message message);
}
