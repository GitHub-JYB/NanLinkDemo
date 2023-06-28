package com.example.NanLinkDemo.mvp.presenter;

import com.example.NanLinkDemo.DB.bean.Controller;
import com.example.NanLinkDemo.bean.AddFixtureType;

import java.util.ArrayList;
import java.util.List;

public interface AddNewFixturePresenter {
    void getListDataFromView();

    void onClickSwitch(int position);

    void receiveListData(ArrayList<AddFixtureType> arrayList);

    void receiveControllerList(List<Controller> controllers);

}
