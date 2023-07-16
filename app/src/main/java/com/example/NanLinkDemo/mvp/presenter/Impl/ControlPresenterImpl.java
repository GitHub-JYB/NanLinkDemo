package com.example.NanLinkDemo.mvp.presenter.Impl;

import android.view.View;

import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.FixtureGroup;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.bean.DeviceDataMessage;
import com.example.NanLinkDemo.mvp.model.Impl.ControlModelImpl;
import com.example.NanLinkDemo.mvp.presenter.ControlPresenter;
import com.example.NanLinkDemo.mvp.view.ControlView;
import com.example.NanLinkDemo.mvp.widget.ControlActivity;
import com.google.gson.Gson;

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
        switch (v.getId()){
            case R.id.left_btn:
                view.finish();
        }
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
        if (!fixtureGroups.isEmpty()) {
            fixtureGroup = fixtureGroups.get(0);
            view.setTitle(fixtureGroup.getName());
            view.setSecondTitle("设备数量: " + fixtureGroup.getFixtureNum());
            showData(fixtureGroup.getData());

        }
    }

    @Override
    public void receiveFixtureList(List<Fixture> fixtures) {
        if (!fixtures.isEmpty()) {
            fixture = fixtures.get(0);
            view.setTitle(fixture.getName());
            view.setSecondTitle("CH: " + fixture.getCH() + "    " + fixture.getConnectType());
            showData(fixture.getData());
        }
    }

    private void showData(String data) {
        Gson gson = new Gson();
        DeviceDataMessage.Data deviceData = gson.fromJson(data, DeviceDataMessage.Data.class);
        view.setMode(deviceData.getFlmModeList().get(0).getRemark());
        String fan = deviceData.getFan().get(0);
        switch (fan) {
            case "FAN_SMART":
                view.setFan(R.drawable.ic_full_fan);
                break;
            case "FAN_FULL":
                if (deviceData.getFan().size() == 2) {
                    view.setFan(R.drawable.ic_on_fan);
                } else {
                    view.setFan(R.drawable.ic_full_fan);
                }
                break;
            case "FAN_HALF":
                view.setFan(R.drawable.ic_half_fan);
                break;
            case "FAN_OFF":
                if (deviceData.getFan().size() == 2) {
                    view.setFan(R.drawable.ic_off_fan);
                } else {
                    view.setFan(R.drawable.ic_slient_fan);
                }
                break;
        }
    }
}
