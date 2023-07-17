package com.example.NanLinkDemo.mvp.presenter.Impl;

import android.view.View;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.FixtureGroup;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.mvp.model.Impl.ManageFixtureModelImpl;
import com.example.NanLinkDemo.mvp.presenter.ManageFixturePresenter;
import com.example.NanLinkDemo.mvp.view.ManageFixtureView;
import com.example.NanLinkDemo.util.SortUtil;

import java.util.ArrayList;
import java.util.List;

public class ManageFixturePresenterImpl implements ManageFixturePresenter {
    private final ManageFixtureView view;
    private final ManageFixtureModelImpl model;
    private ArrayList<Fixture> fixtureList;

    private ArrayList<Fixture> hasGroupFixtureList;

    private ArrayList<Fixture> noGroupFixtureList;
    private int fixtureNum, completeNum;

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
                fixtureNum = completeNum = 0;
                for (Fixture fixture : fixtureList){
                    if (fixture.getFixtureGroupName().equals(view.getFixtureGroupName())){
                        fixtureNum++;
                    }
                    model.updateFixture(fixture);
                }
                break;
        }
    }

    @Override
    public void receiveFixtureList(String fixtureGroupName, List<Fixture> fixtures) {
        if (fixtureGroupName.equals("")){
            noGroupFixtureList = (ArrayList<Fixture>) fixtures;
            fixtureList = SortUtil.sortFixtureList(noGroupFixtureList, MyApplication.getScene().getSortPosition());
            if (!view.getFixtureGroupName().equals("")){
                model.getFixtureList(view.getFixtureGroupName());
            }
        }else {
            hasGroupFixtureList = SortUtil.sortFixtureList((ArrayList<Fixture>) fixtures, MyApplication.getScene().getSortPosition());
            for (int i = hasGroupFixtureList.size() - 1; i >= 0; i--){
                fixtureList.add(0, hasGroupFixtureList.get(i));
            }
        }
        view.showFixture(fixtureList);

    }

    @Override
    public void completeUpdateFixture() {
        completeNum++;
        if (completeNum == fixtureList.size()){
            for (FixtureGroup fixtureGroup : MyApplication.getFixtureGroups()){
                if(fixtureGroup.getName().equals(view.getFixtureGroupName())){
                    fixtureGroup.setFixtureNum(fixtureNum);
                    model.updateFixtureGroup(fixtureGroup);
                    break;
                }
            }
        }
    }

    @Override
    public void completeUpdateFixtureGroup() {
        view.stopLoading();
        view.finish();
    }
}
