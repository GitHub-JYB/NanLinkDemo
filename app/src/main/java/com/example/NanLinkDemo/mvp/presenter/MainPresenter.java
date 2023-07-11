package com.example.NanLinkDemo.mvp.presenter;


import android.view.View;

import com.example.NanLinkDemo.DB.bean.Scene;
import com.example.NanLinkDemo.DB.bean.SceneGroup;
import com.example.NanLinkDemo.DB.bean.User;
import com.example.NanLinkDemo.bean.DeviceMessage;
import com.example.NanLinkDemo.bean.Menu;

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


    void getOnlineUserFromModel();

    void receiveOnlineUser(List<User> users);
}
