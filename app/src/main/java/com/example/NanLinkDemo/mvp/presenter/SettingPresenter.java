package com.example.NanLinkDemo.mvp.presenter;

import com.example.NanLinkDemo.bean.Menu;

import java.util.ArrayList;

public interface SettingPresenter {
    void getListDataFromView();

    void showSettingListToView(ArrayList<Menu> settingArrayList);

    void onClickSwitch(String settingText);
}
