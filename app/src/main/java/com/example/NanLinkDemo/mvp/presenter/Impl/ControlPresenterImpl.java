package com.example.NanLinkDemo.mvp.presenter.Impl;

import android.view.View;

import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.FixtureGroup;
import com.example.NanLinkDemo.mvp.model.Impl.ControlModelImpl;
import com.example.NanLinkDemo.mvp.presenter.ControlPresenter;
import com.example.NanLinkDemo.mvp.view.ControlView;
import com.example.NanLinkDemo.mvp.widget.ControlActivity;

import java.util.List;

public class ControlPresenterImpl implements ControlPresenter {
    private final ControlView view;
    private final ControlModelImpl model;
    private FixtureGroup fixtureGroup;
    private Fixture fixture;

    public ControlPresenterImpl(ControlView view) {
        this.view = view;
        this.model = new ControlModelImpl(this);
    }

    @Override
    public void getMenuFromModel() {

    }

    @Override
    public void menuSwitch(int position) {

    }

    @Override
    public void switchOnclick(View v) {

    }

    @Override
    public void getControlDataFromModel(int type, int id) {
        switch (type) {
            case ControlActivity.TYPE_FIXTURE:
                model.getFixture(id);
                break;
            case ControlActivity.TYPE_FIXTURE_GROUP:
                model.getFixtureGroup(id);
                break;
        }
    }

    @Override
    public void receiveFixtureGroupList(List<FixtureGroup> fixtureGroups) {
        if (!fixtureGroups.isEmpty()){
            fixtureGroup = fixtureGroups.get(0);
            view.setTitle(fixtureGroup.getName());
        }
    }

    @Override
    public void receiveFixtureList(List<Fixture> fixtures) {
        if (!fixtures.isEmpty()){
            fixture = fixtures.get(0);
            view.setTitle(fixture.getName());
        }
    }
}
