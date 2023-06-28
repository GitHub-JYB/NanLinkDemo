package com.example.NanLinkDemo.mvp.presenter;

import android.view.View;

import com.example.NanLinkDemo.DB.bean.Scene;
import com.example.NanLinkDemo.DB.bean.SceneGroup;
import com.example.NanLinkDemo.bean.Menu;

import java.util.ArrayList;
import java.util.List;


public interface SceneGroupPresenter {
    void toolbarSwitch(View v);

    void menuSwitch(int position);

    void sceneListSwitch(int position);

    void getMenuFromModel();

    void getSceneListFromModel(String sceneGroupName);

    void receiveMenu(ArrayList<Menu> menuArrayList);

    void receiveSceneList(List<Scene> scenes);

    void sceneMenuSwitch(int position);

    void receiveThreePointMenu(ArrayList<String> threePointList);

    void updateSceneListToView();

    void switchQuerySceneResult(List<Scene> scenes, int type);

    void getSceneGroupFromModel(String sceneGroupName);

    void receiveSceneGroup(List<SceneGroup> sceneGroups, int type);

    void showSortListToView(ArrayList<Menu> sortArrayList);

    void sortSwitch(int position);

    void showSettingListToView(ArrayList<Menu> settingArrayList);

    void settingSwitch(int position);

    void receiveQuerySceneFromSceneGroup(List<Scene> scenes, int type);
}
