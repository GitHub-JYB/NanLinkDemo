package com.example.nanlinkdemo.mvp.presenter;

import android.view.View;

import com.example.nanlinkdemo.bean.Add24GFixture;
import com.example.nanlinkdemo.ui.BoxView;

import java.util.ArrayList;

public interface Add24GFixturePresenter {
    void getListDataFromView();

    void setDataToView(ArrayList<Add24GFixture> add24GFixtures);

    void setDataToBoxView(BoxView boxView, String title, ArrayList<String> dataList, int checkIndex);

    void getBoxViewDataFromModel(BoxView boxView);

    void onClick();
}
