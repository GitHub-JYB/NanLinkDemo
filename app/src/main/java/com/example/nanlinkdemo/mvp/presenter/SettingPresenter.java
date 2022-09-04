package com.example.nanlinkdemo.mvp.presenter;

import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.bean.RegisterUser;

import java.util.ArrayList;

public interface SettingPresenter {
    void getSettingListFromView();

    void showSettingListToView(ArrayList<Menu> settingArrayList);

    void onClickSwitch(String settingText);
}
