package com.example.nanlinkdemo.mvp.presenter;


import android.view.View;

import com.example.nanlinkdemo.DB.bean.Scene;
import com.example.nanlinkdemo.DB.bean.SceneGroup;
import com.example.nanlinkdemo.DB.bean.User;
import com.example.nanlinkdemo.bean.DeviceMessage;
import com.example.nanlinkdemo.bean.Menu;

import java.util.ArrayList;
import java.util.List;

public interface MainPresenter {


    void getMenuFromModel();

    void showMenuToView(ArrayList<Menu> menuArrayList);

    void menuSwitch(int position);

    void toolbarSwitch(View v);

    void sceneListSwitch(int position);

    void getSceneListFromModel();

    void showSceneListToView(List<Scene> sceneList, List<SceneGroup> sceneGroupList);

    void updateSceneListToView();

    void receiveThreePointMenu(ArrayList<String> threePointList);

    void completeGetScene(List<Scene> scenes);

    void completeGetSceneGroup(List<SceneGroup> sceneGroups);

    void switchQuerySceneResult(List<Scene> scenes, int type);

    void switchQuerySceneGroupResult(List<SceneGroup> sceneGroups, int type);

    void sceneMenuSwitch(int position);

    void showSortListToView(ArrayList<Menu> sortArrayList);

    void sortSwitch(int position);

    void receiveQuerySceneFromSceneGroup(List<Scene> scenes, int type);

    void getDeviceListFromModel();

    void receiveDeviceList(DeviceMessage deviceMessage);
}
