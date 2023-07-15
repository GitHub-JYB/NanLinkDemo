package com.example.NanLinkDemo.mvp.presenter;

import android.view.View;

import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.FixtureGroup;

import java.util.List;

public interface ControlPresenter {
    void getMenuFromModel();

    void menuSwitch(int position);

    void switchOnclick(View v);

    void getControlDataFromModel(int type, int id);

    void receiveFixtureGroupList(List<FixtureGroup> fixtureGroups);

    void receiveFixtureList(List<Fixture> fixtures);
}
