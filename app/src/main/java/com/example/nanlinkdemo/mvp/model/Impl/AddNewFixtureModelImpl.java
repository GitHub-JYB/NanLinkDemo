package com.example.nanlinkdemo.mvp.model.Impl;

import com.example.nanlinkdemo.bean.AddFixtureType;
import com.example.nanlinkdemo.mvp.model.AddNewFixtureModel;
import com.example.nanlinkdemo.mvp.presenter.Impl.AddNewFixturePresenterImpl;

import java.util.ArrayList;

public class AddNewFixtureModelImpl implements AddNewFixtureModel {
    private final AddNewFixturePresenterImpl presenter;

    public AddNewFixtureModelImpl(AddNewFixturePresenterImpl presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getListData() {
        ArrayList<AddFixtureType> arrayList = new ArrayList<AddFixtureType>();
        arrayList.add(new AddFixtureType("通过蓝牙", "直接连接"));
        arrayList.add(new AddFixtureType("通过2.4G","需要信号控制盒"));
        presenter.receiveListData(arrayList);
    }
}
