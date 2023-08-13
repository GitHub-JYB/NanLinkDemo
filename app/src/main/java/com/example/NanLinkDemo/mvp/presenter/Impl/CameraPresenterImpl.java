package com.example.NanLinkDemo.mvp.presenter.Impl;

import android.view.View;

import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.FixtureGroup;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.mvp.model.Impl.CameraModelImpl;
import com.example.NanLinkDemo.mvp.presenter.CameraPresenter;
import com.example.NanLinkDemo.mvp.view.CameraView;
import com.example.NanLinkDemo.mvp.widget.ControlActivity;

import java.util.List;

public class CameraPresenterImpl implements CameraPresenter {
    private final CameraView view;
    private final CameraModelImpl model;
    private int type, id;

    public CameraPresenterImpl(CameraView view) {
        this.view = view;
        this.model = new CameraModelImpl(this);
    }

    @Override
    public void switchOnclick(View v) {
        switch (v.getId()){
            case R.id.toolbar_left_btn:
                view.finish();

        }
    }

    @Override
    public void getControlDataFromModel(int type, int id) {
        this.type = type;
        this.id = id;
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
    public void receiveFixtureList(List<Fixture> fixtures) {

    }

    @Override
    public void receiveFixtureGroupList(List<FixtureGroup> fixtureGroups) {

    }
}
