package com.example.nanlinkdemo.mvp.view;

import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.bean.RegisterUser;

import java.util.ArrayList;

public interface UserSettingView {
    void setPresenter();

    void showMenuDialog(String title, String message, int type);

    void showStories(ArrayList<Menu> settingList, ArrayList<RegisterUser> userList);

    void finish();

    void saveLogin();

}
