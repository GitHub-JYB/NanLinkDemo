package com.example.NanLinkDemo.mvp.view;

import com.example.NanLinkDemo.DB.bean.Controller;

import java.util.ArrayList;

public interface ControllerView {
    void setPresenter();

    void showController(ArrayList<Controller> controllerList);

    void finish();
}
