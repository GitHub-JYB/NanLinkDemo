package com.example.nanlinkdemo.mvp.presenter;

import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.bean.RegisterUser;

import java.util.ArrayList;

public interface UserSettingPresenter {
    void getSettingListFromView();

    void showSettingListToView(ArrayList<Menu> settingArrayList, ArrayList<RegisterUser> userArrayList);

    void settingSwitch(String settingText);

}
