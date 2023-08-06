package com.example.NanLinkDemo.mvp.presenter;

import android.view.View;

import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.FixtureGroup;
import com.example.NanLinkDemo.bean.DeviceDataMessage;
import com.example.NanLinkDemo.bean.Menu;

import java.util.ArrayList;
import java.util.List;

public interface ControlPresenter {
    void getMenuFromModel();

    void menuSwitch(int position);

    void switchOnclick(View v);

    void getControlDataFromModel(int type, int id);

    void receiveFixtureGroupList(List<FixtureGroup> fixtureGroups);

    void receiveFixtureList(List<Fixture> fixtures);

    void switchModeChange(int position);

    void receiveFixtureMenu(ArrayList<Menu> menuArrayList);

    void receiveFixtureGroupMenu(ArrayList<Menu> menuArrayList);

    void clickDelayTime(int position, String delayTime);

    void clickData(int position, String dim);

    void updateData(int position, DeviceDataMessage.Control control);
}
