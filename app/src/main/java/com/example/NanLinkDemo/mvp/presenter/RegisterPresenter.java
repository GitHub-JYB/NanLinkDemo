package com.example.NanLinkDemo.mvp.presenter;


import android.view.View;

import com.example.NanLinkDemo.bean.Message;

public interface RegisterPresenter {

    void switchOnclick(View view);

    void checkRegisterMessage();

    void sendMesToView(Message message, String function);

    void showWarnToView();

    void endCountDown();

    void sendProgressToView(Long aLong);
}
