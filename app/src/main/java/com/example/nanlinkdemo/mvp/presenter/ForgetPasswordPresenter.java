package com.example.nanlinkdemo.mvp.presenter;

import android.view.View;

import com.example.nanlinkdemo.bean.Message;

public interface ForgetPasswordPresenter {
    void switchOnclick(View v);

    void checkCode();

    void sendMesToView(Message message, String function);
}
