package com.example.NanLinkDemo.mvp.presenter.Impl;


import android.view.View;


import com.alibaba.android.arouter.launcher.ARouter;
import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.FixtureGroup;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.bean.DeviceDataMessage;
import com.example.NanLinkDemo.bean.Menu;
import com.example.NanLinkDemo.mvp.model.Impl.ControlModelImpl;
import com.example.NanLinkDemo.mvp.presenter.ControlPresenter;
import com.example.NanLinkDemo.mvp.view.ControlView;
import com.example.NanLinkDemo.mvp.widget.ControlActivity;
import com.example.NanLinkDemo.util.Constant;
import com.example.NanLinkDemo.util.TransformUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class ControlPresenterImpl implements ControlPresenter {
    private final ControlView view;
    private final ControlModelImpl model;
    private FixtureGroup fixtureGroup;
    private Fixture fixture;
    private int type, id;
    private int modeIndex;
    private ArrayList<Menu> menuArrayList;
    private DeviceDataMessage.Data deviceData;

    public ControlPresenterImpl(ControlView view) {
        this.view = view;
        this.model = new ControlModelImpl(this);
    }

    @Override
    public void getMenuFromModel() {
        switch (type) {
            case ControlActivity.TYPE_FIXTURE:
                model.getFixtureMenu();
                break;
            case ControlActivity.TYPE_FIXTURE_GROUP:
                model.getFixtureGroupMenu();
                break;
        }
    }

    @Override
    public void menuSwitch(int position) {
        switch (position) {
            case 1:
                ARouter.getInstance().build(Constant.ACTIVITY_URL_Test).navigation();
                break;
        }
    }

    @Override
    public void switchOnclick(View v) {
        switch (v.getId()) {
            case R.id.left_btn:
                view.finish();
                break;
            case R.id.right_btn:
                view.initMenu();
                break;
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
    public void receiveFixtureGroupList(List<FixtureGroup> fixtureGroups) {
        if (!fixtureGroups.isEmpty()) {
            fixtureGroup = fixtureGroups.get(0);
            view.setTitle(fixtureGroup.getName());
            view.setSecondTitle("设备数量: " + fixtureGroup.getFixtureNum());
            modeIndex = fixtureGroup.getModeIndex();
            showData(fixtureGroup.getData());

        }
    }

    @Override
    public void receiveFixtureList(List<Fixture> fixtures) {
        if (!fixtures.isEmpty()) {
            fixture = fixtures.get(0);
            view.setTitle(fixture.getName());
            view.setSecondTitle("CH: " + TransformUtil.updateCH(fixture.getCH()) + "    " + fixture.getConnectType());
            modeIndex = fixture.getModeIndex();
            showData(fixture.getData());
        }
    }

    @Override
    public void switchModeChange(int position) {
        modeIndex = position;
        switch (type) {
            case ControlActivity.TYPE_FIXTURE:
                fixture.setModeIndex(modeIndex);
                view.setData(deviceData.getFlmModeList().get(modeIndex).getFirstControls());
                model.updateFixture(fixture);
                break;
            case ControlActivity.TYPE_FIXTURE_GROUP:
                fixtureGroup.setModeIndex(modeIndex);
                view.setData(deviceData.getFlmModeList().get(modeIndex).getFirstControls());
                model.updateFixtureGroup(fixtureGroup);
                break;
        }
    }

    @Override
    public void receiveFixtureMenu(ArrayList<Menu> menuArrayList) {
        this.menuArrayList = menuArrayList;
        if (deviceData.getFan().size() < 2) {
            for (Menu menu : menuArrayList) {
                if (menu.getText().equals("风扇控制")) {
                    menuArrayList.remove(menu);
                    break;
                }
            }
        }
        view.showMenu(menuArrayList);
        view.openDrawLayout();
    }

    @Override
    public void receiveFixtureGroupMenu(ArrayList<Menu> menuArrayList) {
        this.menuArrayList = menuArrayList;
        if (deviceData.getFan().size() < 2) {
            for (Menu menu : menuArrayList) {
                if (menu.getText().equals("风扇控制")) {
                    menuArrayList.remove(menu);
                    break;
                }
            }
        }
        if (!deviceData.getFlmModeList().get(modeIndex).getRemark().equals("像素特效模式")) {
            for (Menu menu : menuArrayList) {
                if (menu.getText().equals("群组模式")) {
                    menuArrayList.remove(menu);
                    break;
                }
            }
        }
        view.showMenu(menuArrayList);
        view.openDrawLayout();
    }

    @Override
    public void clickDelayTime(int position, String delayTime) {

    }

    @Override
    public void clickData(int position, String dim) {

    }

    @Override
    public void updateData(int position, DeviceDataMessage.Control control) {
        deviceData.getFlmModeList().get(modeIndex).getFirstControls().set(position, control);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String data = gson.toJson(deviceData);
        switch (type) {
            case ControlActivity.TYPE_FIXTURE:
                fixture.setData(data);
                model.updateFixture(fixture);
                break;
            case ControlActivity.TYPE_FIXTURE_GROUP:
                fixtureGroup.setData(data);
                model.updateFixtureGroup(fixtureGroup);
                break;
        }

    }

    @Override
    public void gotoCameraWithData() {
        ARouter.getInstance().build(Constant.ACTIVITY_URL_Camera).withInt("type", type).withInt("id", id).navigation();
    }

    private void showData(String data) {
        Gson gson = new Gson();
        deviceData = gson.fromJson(data, DeviceDataMessage.Data.class);
        if (deviceData.getFan().size() > 1) {
            String fan = deviceData.getFan().get(0);
            switch (fan) {
                case "FAN_SMART":
                    view.setFan(R.drawable.ic_smart_fan);
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
        } else {
            view.setFanVisibility(View.GONE);
        }
        view.setMode(deviceData.getFlmModeList().get(modeIndex).getRemark());
        view.setModeList(deviceData.getFlmModeList());
        view.setDimAndDelayTime(Integer.parseInt(deviceData.getDimItem()), Integer.parseInt(deviceData.getDimDelay()));
        view.setData(deviceData.getFlmModeList().get(modeIndex).getFirstControls());
    }
}
