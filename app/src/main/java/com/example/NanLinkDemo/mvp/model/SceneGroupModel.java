package com.example.NanLinkDemo.mvp.model;

import com.example.NanLinkDemo.DB.bean.Scene;
import com.example.NanLinkDemo.DB.bean.SceneGroup;
import com.example.NanLinkDemo.DB.bean.User;

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

    void getSortList();

    void updateUser(User user);

    void getSettingList();

    void deleteSceneGroup(SceneGroup sceneGroup);

    void querySceneFromGroup(String sceneGroupName, int type);
}
