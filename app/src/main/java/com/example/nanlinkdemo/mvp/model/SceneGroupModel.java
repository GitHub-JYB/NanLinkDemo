package com.example.nanlinkdemo.mvp.model;

import com.example.nanlinkdemo.DB.bean.Scene;
import com.example.nanlinkdemo.DB.bean.SceneGroup;

public interface SceneGroupModel {
    void getMenu();

    void getSceneList(String name);

    void getThreePointMenu(int scenePosition);

    void deleteScene(Scene scene);

    void queryScene(String sceneName);

    void addScene(Scene scene);

    void querySceneGroup(String sceneGroupName);

    void updateSceneGroup(SceneGroup sceneGroup);
}
