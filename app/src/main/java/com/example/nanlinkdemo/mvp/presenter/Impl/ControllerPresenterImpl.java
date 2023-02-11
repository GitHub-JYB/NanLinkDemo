package com.example.nanlinkdemo.mvp.presenter.Impl;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.nanlinkdemo.DB.bean.Controller;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.mvp.model.Impl.ControllerModelImpl;
import com.example.nanlinkdemo.mvp.presenter.ControllerPresenter;
import com.example.nanlinkdemo.mvp.view.ControllerView;
import com.example.nanlinkdemo.util.Constant;

import java.util.ArrayList;
import java.util.List;

public class ControllerPresenterImpl implements ControllerPresenter {
    private final ControllerView view;
    private final ControllerModelImpl model;
    private ArrayList<Controller> controllerList;

    public ControllerPresenterImpl(ControllerView view) {
        this.view = view;
        this.model = new ControllerModelImpl(this);
    }

    @Override
    public void getListDataFromView() {
        model.getListData();
    }

    @Override
    public void onClickSwitch(int position) {

    }

    @Override
    public void receiveControllerList(List<Controller> controllers) {
        controllerList = (ArrayList<Controller>)controllers;
        view.showController(controllerList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_left_btn:
                view.finish();
                break;
            case R.id.toolbar_right_btn:
                ARouter.getInstance().build(Constant.ACTIVITY_URL_ScanController).navigation();
                break;
        }
    }
}
