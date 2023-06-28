package com.example.NanLinkDemo.mvp.presenter;

import android.view.View;

import com.example.NanLinkDemo.bean.Message;

public interface FeedbackPresenter {
    void switchOnclick(View v);

    void checkText();

    void sendMesToView(Message message);
}
