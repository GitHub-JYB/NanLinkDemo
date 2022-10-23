package com.example.nanlinkdemo.mvp.presenter;

import android.view.View;

import com.example.nanlinkdemo.bean.Message;

public interface FeedbackPresenter {
    void switchOnclick(View v);

    void checkText();

    void sendMesToView(Message message);
}
