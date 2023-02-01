package com.example.nanlinkdemo.mvp.presenter.Impl;

import com.example.nanlinkdemo.bean.AddFixtureType;
import com.example.nanlinkdemo.mvp.model.Impl.AddNewFixtureModelImpl;
import com.example.nanlinkdemo.mvp.presenter.AddNewFixturePresenter;
import com.example.nanlinkdemo.mvp.view.AddNewFixtureView;

import java.util.ArrayList;

public class AddNewFixturePresenterImpl implements AddNewFixturePresenter {
    private final AddNewFixtureView view;
    private final AddNewFixtureModelImpl model;

    public AddNewFixturePresenterImpl(AddNewFixtureView view) {
        this.view = view;
        this.model = new AddNewFixtureModelImpl(this);
    }

    @Override
    public void getListDataFromView() {
        model.getListData();
    }

    @Override
    public void onClickSwitch(String settingText) {

    }

    @Override
    public void receiveListData(ArrayList<AddFixtureType> arrayList) {
        view.showAddType(arrayList);
    }
}
