package com.example.NanLinkDemo.mvp.presenter;

import android.view.View;

import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.FixtureGroup;
import com.example.NanLinkDemo.DB.bean.Scene;
import com.example.NanLinkDemo.bean.Menu;

import java.util.ArrayList;
import java.util.List;

public interface ScenePresenter {

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

    void showSettingListToView(ArrayList<Menu> settingArrayList);

    void updateFixtureList();

    void receiveFixtureGroupMenu(ArrayList<String> fixtureGroupMenuList, int fixtureGroupPosition);

    void receiveFixtureMenu(ArrayList<String> fixtureMenuList, int fixturePosition);

    void receiveFixtureMenu(ArrayList<String> fixtureMenuList,
                            Fixture fixture);

    void receiveFixtureGroupList(List<FixtureGroup> fixtureGroups);

    void receiveFixtureList(List<Fixture> fixtures);

    void receiveSceneGroup(List<Scene> scenes);

    void FixtureMenuSwitch(Fixture fixture);

    void FixtureListDim(int position);
}
