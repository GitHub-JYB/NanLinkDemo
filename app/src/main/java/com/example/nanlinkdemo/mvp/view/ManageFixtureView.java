package com.example.nanlinkdemo.mvp.view;

import com.example.nanlinkdemo.DB.bean.Fixture;

import java.util.ArrayList;

public interface ManageFixtureView {
    void setPresenter();

    void showFixture(ArrayList<Fixture> fixtures);

    void finish();

    void startLoading();

    void stopLoading();

    String getFixtureGroupName();
}
