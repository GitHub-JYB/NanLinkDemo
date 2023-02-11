package com.example.nanlinkdemo.mvp.view;

import com.example.nanlinkdemo.DB.bean.Controller;

import java.util.ArrayList;

public interface ControllerView {
    void setPresenter();

    void showController(ArrayList<Controller> controllerList);

    void finish();
}
