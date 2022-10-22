package com.example.nanlinkdemo.mvp.presenter;


import android.view.View;

import com.example.nanlinkdemo.DB.bean.User;
import com.example.nanlinkdemo.bean.Message;

import java.util.List;

public interface LoginPresenter {

    void sendMesToView(Message mes);

    void switchOnclick(View view);

    void receiveUser(List<User> users);

    void completeUpdateUser();

    void completeAddUser();

    void getLastUserFromModel();

    void receiveLastUser(List<User> users);
}
