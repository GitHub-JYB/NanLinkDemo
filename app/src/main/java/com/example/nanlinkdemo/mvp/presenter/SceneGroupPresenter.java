package com.example.nanlinkdemo.mvp.presenter;

import android.view.View;

import com.example.nanlinkdemo.DB.bean.Scene;
import com.example.nanlinkdemo.DB.bean.SceneGroup;
import com.example.nanlinkdemo.bean.Menu;

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

    void receiveThreePointMenu(ArrayList<String> threePointList, int scenePosition);

    void updateSceneListToView();

    void switchQuerySceneResult(String inputText, List<Scene> scenes);

    void getSceneGroupFromModel(String sceneGroupName);

    void updateSceneGroup(List<SceneGroup> sceneGroups);
}
