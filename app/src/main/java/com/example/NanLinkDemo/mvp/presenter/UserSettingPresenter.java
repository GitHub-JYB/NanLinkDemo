package com.example.NanLinkDemo.mvp.presenter;

import com.example.NanLinkDemo.bean.Menu;
import com.example.NanLinkDemo.bean.RegisterUser;

import java.util.ArrayList;

public interface UserSettingPresenter {

    void showSettingListToView(ArrayList<Menu> settingArrayList, ArrayList<RegisterUser> userArrayList);

    void settingSwitch(String settingText);

    void getSettingListFromModel();
}
