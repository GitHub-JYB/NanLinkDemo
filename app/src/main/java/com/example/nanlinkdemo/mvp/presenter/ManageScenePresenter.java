package com.example.nanlinkdemo.mvp.presenter;

import android.view.View;

import com.example.nanlinkdemo.DB.bean.Scene;
import com.example.nanlinkdemo.DB.bean.SceneGroup;

import java.util.List;

public interface ManageScenePresenter {
    void getSceneListFromModel();

    void switchOnclick(View view);

    void switchSceneList(int position);

    void receiveQueryScene(List<Scene> scenes, int type);

    void updateSceneList();

    void receiveQuerySceneGroup(List<SceneGroup> sceneGroups);

    void getSceneGroupFromModel(String sceneGroupName);
}
