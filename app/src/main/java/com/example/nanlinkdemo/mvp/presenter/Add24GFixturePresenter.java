package com.example.nanlinkdemo.mvp.presenter;

import com.example.nanlinkdemo.bean.Add24GFixture;

import java.util.ArrayList;

public interface Add24GFixturePresenter {
    void getListDataFromView();

    void setDataToView(ArrayList<Add24GFixture> add24GFixtures);
}
