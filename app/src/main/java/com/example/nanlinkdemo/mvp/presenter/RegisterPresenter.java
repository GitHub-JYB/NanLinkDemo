package com.example.nanlinkdemo.mvp.presenter;


import android.view.View;

import com.example.nanlinkdemo.bean.Message;

public interface RegisterPresenter {

    void switchOnclick(View view);

    void checkRegisterMessage();

    void sendMesToView(Message message, String function);

    void endCountDown();

    void sendProgressToView(Long aLong);
}
