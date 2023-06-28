package com.example.NanLinkDemo.mvp.presenter.Impl;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.NanLinkDemo.DB.bean.Controller;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.mvp.model.Impl.ControllerModelImpl;
import com.example.NanLinkDemo.mvp.presenter.ControllerPresenter;
import com.example.NanLinkDemo.mvp.view.ControllerView;
import com.example.NanLinkDemo.util.Constant;

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
