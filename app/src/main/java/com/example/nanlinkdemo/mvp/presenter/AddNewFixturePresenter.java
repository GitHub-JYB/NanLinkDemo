package com.example.nanlinkdemo.mvp.presenter;

import com.example.nanlinkdemo.bean.AddFixtureType;

import java.util.ArrayList;

public interface AddNewFixturePresenter {
    void getListDataFromView();

    void onClickSwitch(int position);

    void receiveListData(ArrayList<AddFixtureType> arrayList);
}
