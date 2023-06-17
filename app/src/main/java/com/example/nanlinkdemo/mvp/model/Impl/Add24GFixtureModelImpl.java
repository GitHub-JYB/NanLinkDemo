package com.example.nanlinkdemo.mvp.model.Impl;

import com.example.nanlinkdemo.bean.Add24GFixture;
import com.example.nanlinkdemo.mvp.model.Add24GFixtureModel;
import com.example.nanlinkdemo.mvp.presenter.Impl.Add24GFixturePresenterImpl;

import java.util.ArrayList;

public class Add24GFixtureModelImpl implements Add24GFixtureModel {
    private final Add24GFixturePresenterImpl presenter;

    public Add24GFixtureModelImpl(Add24GFixturePresenterImpl presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getListData() {
        ArrayList<Add24GFixture> add24GFixtures = new ArrayList<>();
        add24GFixtures.add(new Add24GFixture("",""));
        presenter.setDataToView(add24GFixtures);
    }
}
