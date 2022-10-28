package com.example.nanlinkdemo.mvp.presenter;

import android.view.View;

import com.example.nanlinkdemo.DB.bean.Scene;
import com.example.nanlinkdemo.bean.Menu;

import java.util.ArrayList;
import java.util.List;

public interface ScenePresenter {
    void getSceneFromModel(String sceneName);

    void switchOnclick(View v);

    void sortSwitch(int position);

    void settingSwitch(int position);

    void getFixtureListFromModel(String sceneName);

    void getMenuFromModel();

    void menuSwitch(int position);

    void FixtureListSwitch(int position);

    void FixtureMenuSwitch(int position);

    void receiveMenu(ArrayList<Menu> menuArrayList);

    void showSortListToView(ArrayList<Menu> sortArrayList);

    void receiveSceneList(List<Scene> scenes);

    void showSettingListToView(ArrayList<Menu> settingArrayList);
}
