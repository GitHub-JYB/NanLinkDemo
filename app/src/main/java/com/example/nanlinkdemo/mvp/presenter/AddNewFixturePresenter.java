package com.example.nanlinkdemo.mvp.presenter;

import com.example.nanlinkdemo.DB.bean.Controller;
import com.example.nanlinkdemo.bean.AddFixtureType;

import java.util.ArrayList;
import java.util.List;

public interface AddNewFixturePresenter {
    void getListDataFromView();

    void onClickSwitch(int position);

    void receiveListData(ArrayList<AddFixtureType> arrayList);

    void receiveControllerList(List<Controller> controllers);

}
