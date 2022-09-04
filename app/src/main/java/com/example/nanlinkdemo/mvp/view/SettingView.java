package com.example.nanlinkdemo.mvp.view;

import com.example.nanlinkdemo.bean.Menu;

import java.util.ArrayList;

public interface SettingView {
    void setPresenter();

    void showStories(ArrayList<Menu> settingList);

}
