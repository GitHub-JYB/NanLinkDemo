package com.example.nanlinkdemo.mvp.model;

import com.example.nanlinkdemo.DB.bean.Scene;
import com.example.nanlinkdemo.DB.bean.SceneGroup;
import com.example.nanlinkdemo.DB.bean.User;

public interface MainModel {
    void getMenu(String nickName);

    void getSceneList();

    void getSceneGroupList();

    void getThreePointMenu();

    void deleteScene(Scene scene);

    void addScene(Scene scene);

    void addSceneGroup(SceneGroup sceneGroup);

    void deleteSceneGroup(SceneGroup sceneGroup);

    void queryScene(String inputText, int type);

    void querySceneGroup(String inputText, int type);

    void updateScene(Scene scene);

    void updateSceneGroup(SceneGroup sceneGroup);

    void getSortList(int position);

    void updateUser(User user);
}
