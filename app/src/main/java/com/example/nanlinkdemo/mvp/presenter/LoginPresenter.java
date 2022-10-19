package com.example.nanlinkdemo.mvp.presenter;


import android.view.View;

import com.example.nanlinkdemo.bean.Message;

public interface LoginPresenter {

    void sendMesToView(Message mes);

    void switchOnclick(View view);

}
