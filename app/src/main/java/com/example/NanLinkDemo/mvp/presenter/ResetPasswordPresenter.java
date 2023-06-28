package com.example.NanLinkDemo.mvp.presenter;

import android.view.View;

import com.example.NanLinkDemo.bean.Message;

public interface ResetPasswordPresenter {
    void switchOnclick(View v);

    void checkCode();

    void sendMesToView(Message message, String function);

    void updateProgressCountToView(Long aLong);

    void endCountToView();
}
