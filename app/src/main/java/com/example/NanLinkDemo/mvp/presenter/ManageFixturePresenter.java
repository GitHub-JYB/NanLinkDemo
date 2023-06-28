package com.example.NanLinkDemo.mvp.presenter;

import android.view.View;

import com.example.NanLinkDemo.DB.bean.Fixture;

import java.util.List;

public interface ManageFixturePresenter {
    void getFixtureListFromModel();

    void switchFixtureList(int position);

    void switchOnclick(View view);

    void receiveFixtureList(String fixtureGroupName, List<Fixture> fixtures);
}
