package com.example.nanlinkdemo.mvp.presenter;

import com.example.nanlinkdemo.DB.bean.User;
import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.bean.RegisterUser;

import java.util.ArrayList;
import java.util.List;

public interface UserSettingPresenter {
    void getSettingListFromView();

    void showSettingListToView(ArrayList<Menu> settingArrayList, ArrayList<RegisterUser> userArrayList);

    void settingSwitch(String settingText);

    void receiveLastUser(List<User> users);

    void receiveOnlineUser(List<User> users);
}
