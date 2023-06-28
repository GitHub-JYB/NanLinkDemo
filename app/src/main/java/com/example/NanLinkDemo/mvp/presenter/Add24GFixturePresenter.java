package com.example.NanLinkDemo.mvp.presenter;

import com.example.NanLinkDemo.bean.Add24GFixture;

import java.util.ArrayList;

public interface Add24GFixturePresenter {
    void getDataFromView();

    void setDataToView(ArrayList<Add24GFixture> add24GFixtures);

    void setDataToBoxView(Integer boxViewId, String title, ArrayList<String> dataList, int checkIndex);

    void onClick();

    void setListener();
}
