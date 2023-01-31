package com.example.nanlinkdemo.mvp.presenter;

import android.view.View;

import com.example.nanlinkdemo.DB.bean.Fixture;
import com.example.nanlinkdemo.DB.bean.FixtureGroup;
import com.example.nanlinkdemo.DB.bean.Scene;
import com.example.nanlinkdemo.bean.Menu;

import java.util.ArrayList;
import java.util.List;

public interface ScenePresenter {
    void getSceneFromModel(String sceneName);

    void switchOnclick(View v);

    void sortSwitch(int position);

    void settingSwitch(int position);

    void getFixtureListFromModel();

    void getMenuFromModel();

    void menuSwitch(int position);

    void FixtureListSwitch(int position);

    void FixtureMenuSwitch(int position);

    void receiveMenu(ArrayList<Menu> menuArrayList);

    void showSortListToView(ArrayList<Menu> sortArrayList);

    void receiveSceneList(List<Scene> scenes, int type);

    void showSettingListToView(ArrayList<Menu> settingArrayList);

    void receiveAllFixture(List<Fixture> fixtures, int type);

    void receiveFixtureGroup(List<FixtureGroup> fixtureGroups, int type);

    void updateFixtureList();

    void receiveFixtureList(List<Fixture> fixtures,  int type);

    void receiveFixtureGroupMenu(ArrayList<String> fixtureGroupMenuList, int fixtureGroupPosition);

    void receiveFixtureMenu(ArrayList<String> fixtureMenuList, int fixturePosition);
}
