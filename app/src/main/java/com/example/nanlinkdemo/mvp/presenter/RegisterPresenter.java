package com.example.nanlinkdemo.mvp.presenter;


import android.view.View;

import com.example.nanlinkdemo.bean.Message;

public interface RegisterPresenter {


    void register(String email, String password, String code, String nickName);

    void sendMesToView(Message mes);

    void switchOnclick(View view);

}
