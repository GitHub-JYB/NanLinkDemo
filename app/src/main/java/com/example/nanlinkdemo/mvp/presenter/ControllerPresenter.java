package com.example.nanlinkdemo.mvp.presenter;

import android.view.View;

import com.example.nanlinkdemo.DB.bean.Controller;

import java.util.List;

public interface ControllerPresenter {
    void getListDataFromView();

    void onClickSwitch(int position);

    void receiveControllerList(List<Controller> controllers);

    void onClick(View v);
}
