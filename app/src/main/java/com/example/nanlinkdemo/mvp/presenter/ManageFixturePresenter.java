package com.example.nanlinkdemo.mvp.presenter;

import android.view.View;

import com.example.nanlinkdemo.DB.bean.Fixture;

import java.util.List;

public interface ManageFixturePresenter {
    void getFixtureListFromModel();

    void switchFixtureList(int position);

    void switchOnclick(View view);

    void receiveFixtureList(List<Fixture> fixtures);
}
