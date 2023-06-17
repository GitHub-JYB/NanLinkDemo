package com.example.nanlinkdemo.mvp.presenter.Impl;

import com.example.nanlinkdemo.bean.Add24GFixture;
import com.example.nanlinkdemo.mvp.model.Impl.Add24GFixtureModelImpl;
import com.example.nanlinkdemo.mvp.presenter.Add24GFixturePresenter;
import com.example.nanlinkdemo.mvp.view.Add24GFixtureView;

import java.util.ArrayList;

public class Add24GFixturePresenterImpl implements Add24GFixturePresenter {
    private final Add24GFixtureView view;
    private final Add24GFixtureModelImpl model;

    public Add24GFixturePresenterImpl(Add24GFixtureView view) {
        this.view = view;
        this.model = new Add24GFixtureModelImpl(this);
    }

    @Override
    public void getListDataFromView() {
        model.getListData();
    }

    @Override
    public void setDataToView(ArrayList<Add24GFixture> add24GFixtures) {
        view.setData(add24GFixtures);
    }
}
