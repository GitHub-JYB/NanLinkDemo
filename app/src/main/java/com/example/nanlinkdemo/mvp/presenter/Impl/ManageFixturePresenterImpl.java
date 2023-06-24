package com.example.nanlinkdemo.mvp.presenter.Impl;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.DB.bean.Fixture;
import com.example.nanlinkdemo.DB.bean.FixtureGroup;
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

    private ArrayList<Fixture> hasGroupFixtureList;

    private ArrayList<Fixture> noGroupFixtureList;

    public ManageFixturePresenterImpl(ManageFixtureView view) {
        this.view = view;
        this.model = new ManageFixtureModelImpl(this);
    }

    @Override
    public void getFixtureListFromModel() {
            model.getFixtureList("");

    }

    @Override
    public void switchFixtureList(int position) {
        if (fixtureList.get(position).getFixtureGroupName().equals("")){
            fixtureList.get(position).setFixtureGroupName(view.getFixtureGroupName());
        }else {
            fixtureList.get(position).setFixtureGroupName("");
        }
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
                int fixtureNum = 0;
                for (Fixture fixture : fixtureList){
                    if (fixture.getFixtureGroupName().equals(view.getFixtureGroupName())){
                        fixtureNum++;
                    }
                    model.updateFixture(fixture);
                }
                for (FixtureGroup fixtureGroup : MyApplication.getFixtureGroups()){
                    if(fixtureGroup.getName().equals(view.getFixtureGroupName())){
                        fixtureGroup.setFixtureNum(fixtureNum);
                        model.updateFixtureGroup(fixtureGroup);
                    }
                }

                view.stopLoading();
                ARouter.getInstance().build(Constant.ACTIVITY_URL_Scene).navigation();
                break;
        }
    }

    @Override
    public void receiveFixtureList(String fixtureGroupName, List<Fixture> fixtures) {
        if (fixtureGroupName.equals("")){
            noGroupFixtureList = (ArrayList<Fixture>) fixtures;
            fixtureList = noGroupFixtureList;
            if (!view.getFixtureGroupName().equals("")){
                model.getFixtureList(view.getFixtureGroupName());
            }
        }else {
            hasGroupFixtureList = (ArrayList<Fixture>) fixtures;
            for (Fixture fixture : hasGroupFixtureList){
                fixtureList.add(0, fixture);
            }
        }
        view.showFixture(fixtureList);

    }
}
