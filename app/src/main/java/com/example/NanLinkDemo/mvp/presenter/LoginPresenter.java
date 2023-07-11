package com.example.NanLinkDemo.mvp.presenter;


import android.view.View;

import com.example.NanLinkDemo.DB.bean.User;
import com.example.NanLinkDemo.bean.Message;

import java.util.List;

public interface LoginPresenter {

    void sendMesToView(Message mes);

    void showWarnToView();

    void switchOnclick(View view);

    void receiveUser(List<User> users);

    void getLastUserFromModel();

    void receiveLastUser(List<User> users);
}
