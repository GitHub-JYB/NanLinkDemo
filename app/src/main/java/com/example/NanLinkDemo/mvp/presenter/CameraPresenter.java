package com.example.NanLinkDemo.mvp.presenter;

import android.view.View;

import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.FixtureGroup;

import java.util.List;

public interface CameraPresenter {
    void switchOnclick(View view);

    void getControlDataFromModel(int type, int id);

    void receiveFixtureList(List<Fixture> fixtures);

    void receiveFixtureGroupList(List<FixtureGroup> fixtureGroups);
}
