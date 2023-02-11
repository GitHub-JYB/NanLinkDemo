package com.example.nanlinkdemo.mvp.presenter.Impl;

import android.content.Intent;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.DB.bean.Fixture;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.mvp.model.Impl.ManageFixtureModelImpl;
import com.example.nanlinkdemo.mvp.presenter.ManageFixturePresenter;
import com.example.nanlinkdemo.mvp.view.ManageFixtureView;
import com.example.nanlinkdemo.util.Constant;

import java.util.ArrayList;
import java.util.List;

public class ManageFixturePresenterImpl implements ManageFixturePresenter {
    private final ManageFixtureView view;
    private final ManageFixtureModelImpl model;
    private ArrayList<Fixture> fixtureList;

    public ManageFixturePresenterImpl(ManageFixtureView view) {
        this.view = view;
        this.model = new ManageFixtureModelImpl(this);
    }

    @Override
    public void getFixtureListFromModel() {
        model.getFixtureList();
    }

    @Override
    public void switchFixtureList(int position) {
        fixtureList.get(position).setFixtureGroupName(MyApplication.getFixtureGroup().getName());
        view.showFixture(fixtureList);
    }

    @Override
    public void switchOnclick(View v) {
        switch (v.getId()){
            case R.id.toolbar_left_btn:
                view.finish();
                break;
            case R.id.finish:
                view.startLoading();
                for (Fixture fixture : fixtureList){
                    if (!fixture.getFixtureGroupName().equals("")){
                        model.updateFixture(fixture);
                        MyApplication.getFixtureGroup().setFixtureNum(MyApplication.getFixtureGroup().getFixtureNum() + 1);
                    }
                }
                model.updateFixtureGroup(MyApplication.getFixtureGroup());
                view.stopLoading();
                ARouter.getInstance().build(Constant.ACTIVITY_URL_Main).navigation();
                break;
        }
    }

    @Override
    public void receiveFixtureList(List<Fixture> fixtures) {
        this.fixtureList = (ArrayList)fixtures;
    }
}
