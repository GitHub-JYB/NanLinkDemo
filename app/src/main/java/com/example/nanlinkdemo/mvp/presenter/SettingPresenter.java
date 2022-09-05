package com.example.nanlinkdemo.mvp.presenter;

import com.example.nanlinkdemo.bean.Menu;

import java.util.ArrayList;

public interface SettingPresenter {
    void getListDataFromView();

    void showSettingListToView(ArrayList<Menu> settingArrayList);

    void onClickSwitch(String settingText);
}
