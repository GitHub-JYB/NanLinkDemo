package com.example.nanlinkdemo.mvp.presenter;

import android.view.View;

import com.example.nanlinkdemo.bean.Add24GFixture;
import com.example.nanlinkdemo.ui.BoxView;

import java.util.ArrayList;

public interface Add24GFixturePresenter {
    void getDataFromView();

    void setDataToView(ArrayList<Add24GFixture> add24GFixtures);

    void setDataToBoxView(Integer boxViewId, String title, ArrayList<String> dataList, int checkIndex);

    void onClick();

    void setListener();
}
