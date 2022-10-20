package com.example.nanlinkdemo.mvp.presenter;


import android.view.View;

import com.example.nanlinkdemo.DB.bean.Scene;
import com.example.nanlinkdemo.DB.bean.SceneGroup;
import com.example.nanlinkdemo.DB.bean.User;
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

    void querySceneToModel(String inputText);

    void querySceneGroupToModel(String inputText);

    void addSceneToModel(String inputText);

    void addSceneGroupToModel(String inputText);

    void updateSceneListToView();

    void getThreePointMenuFromModel(int type, int furtherPosition);

    void showThreePointMenuToView(ArrayList<String> threePointList, int type, int furtherPosition);

    void switchThreePointMenu(int position, int type, int furtherPosition);

    void deleteSceneFromModel(Scene scene);

    void completeGetScene(List<Scene> scenes);

    void completeGetSceneGroup(List<SceneGroup> sceneGroups);

    void deleteSceneGroupFromModel(SceneGroup sceneGroup);

    void switchDialog(String inputText, String title);

    void switchQuerySceneResult(String inputText, List<Scene> scenes);

    void switchQuerySceneGroupResult(String inputText, List<SceneGroup> sceneGroups);

    void getOnlineUserFromModel();

    void receiveOnlineUser(List<User> users);
}
