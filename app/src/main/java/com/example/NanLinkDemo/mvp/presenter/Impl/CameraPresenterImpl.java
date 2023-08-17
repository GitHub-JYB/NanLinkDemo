package com.example.NanLinkDemo.mvp.presenter.Impl;

import android.view.View;

import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.FixtureGroup;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.bean.DeviceDataMessage;
import com.example.NanLinkDemo.mvp.model.Impl.CameraModelImpl;
import com.example.NanLinkDemo.mvp.presenter.CameraPresenter;
import com.example.NanLinkDemo.mvp.view.CameraView;
import com.example.NanLinkDemo.mvp.widget.ControlActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class CameraPresenterImpl implements CameraPresenter {
    private final CameraView view;
    private final CameraModelImpl model;
    private int type, id;
    private Fixture fixture;
    private FixtureGroup fixtureGroup;


    public CameraPresenterImpl(CameraView view) {
        this.view = view;
        this.model = new CameraModelImpl(this);
    }

    @Override
    public void switchOnclick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left_btn:
                view.finish();
                break;
            case R.id.getPhoto:
                view.stopPreview();
                view.updateBtn(false);
                break;
            case R.id.retry:
                view.startPreview();
                view.updateBtn(true);
                break;
            case R.id.complete:
                switch (type) {
                    case ControlActivity.TYPE_FIXTURE:
                        fixture.setData(updateData(fixture.getData()));
                        model.updateFixture(fixture);
                        break;
                    case ControlActivity.TYPE_FIXTURE_GROUP:
                        fixtureGroup.setData(updateData(fixtureGroup.getData()));
                        model.updateFixtureGroup(fixtureGroup);
                        break;
                }
                break;
            case R.id.change_view:
                view.toggleZoom();
                break;
        }
    }

    public void finishUpdate(){
        view.finish();
    }

    private String updateData(String data) {
        Gson gson = new Gson();
        DeviceDataMessage.Data deviceData = gson.fromJson(data, DeviceDataMessage.Data.class);
        for (DeviceDataMessage.FlmMode flmMode : deviceData.getFlmModeList()) {
            if (flmMode.getRemark().equals("彩色模式")){
                flmMode.getFirstControls().get(1).getElements().setHue(String.valueOf(view.getHSI()));
                flmMode.getFirstControls().get(1).getElements().setSat(String.valueOf(view.getSAT()));
                break;
            }
        }
        gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(deviceData);
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
        if (!fixtures.isEmpty()) {
            fixture = fixtures.get(0);
        }
    }

    @Override
    public void receiveFixtureGroupList(List<FixtureGroup> fixtureGroups) {
        if (!fixtureGroups.isEmpty()) {
            fixtureGroup = fixtureGroups.get(0);
        }
    }
}
