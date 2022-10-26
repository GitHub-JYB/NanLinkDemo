package com.example.nanlinkdemo.mvp.model;

import com.example.nanlinkdemo.DB.bean.Scene;
import com.example.nanlinkdemo.DB.bean.SceneGroup;
import com.example.nanlinkdemo.DB.bean.User;

public interface SceneGroupModel {
    void getMenu();

    void getSceneList(String name);

    void getThreePointMenu();

    void deleteScene(Scene scene);

    void queryScene(String sceneName, int type);

    void addScene(Scene scene);

    void querySceneGroup(String sceneGroupName, int type);

    void updateSceneGroup(SceneGroup sceneGroup);

    void updateScene(Scene scene);

    void getSortList(int position);

    void updateUser(User user);

    void getSettingList();
}
