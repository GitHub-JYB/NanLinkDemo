package com.example.NanLinkDemo.mvp.presenter.Impl;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.FixtureGroup;
import com.example.NanLinkDemo.mvp.model.Impl.ControlModeModelImpl;
import com.example.NanLinkDemo.mvp.presenter.ControlModePresenter;
import com.example.NanLinkDemo.mvp.view.ControlModeView;

import java.util.ArrayList;

public class ControlModePresenterImpl implements ControlModePresenter {
    private final ControlModeView view;
    private final ControlModeModelImpl model;
    private ArrayList<Fixture> fixtures;
    private ArrayList<FixtureGroup> fixtureGroups;

    public ControlModePresenterImpl(ControlModeView view) {
        this.view = view;
        this.model = new ControlModeModelImpl(this);
    }

    @Override
    public void updateDim(int position, String dim) {
        if (position > fixtureGroups.size()){

        }
    }

    @Override
    public void getDataList() {
        fixtures = MyApplication.getFixtures();
        fixtureGroups = MyApplication.getFixtureGroups();
        view.showControls(fixtureGroups, fixtures);
    }
}
