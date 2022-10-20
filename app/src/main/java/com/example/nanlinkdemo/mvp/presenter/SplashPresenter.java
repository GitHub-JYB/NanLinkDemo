package com.example.nanlinkdemo.mvp.presenter;

import com.example.nanlinkdemo.DB.bean.User;
import com.example.nanlinkdemo.bean.Message;

import java.util.List;

public interface SplashPresenter {
    void startCountDownFromModel();

    void endCountDown();

    void receiveOnlineUser(List<User> users);

    void sendMesToView(Message message);
}
