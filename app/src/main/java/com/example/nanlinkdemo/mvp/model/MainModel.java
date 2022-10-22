package com.example.nanlinkdemo.mvp.model;

import com.example.nanlinkdemo.DB.bean.Scene;
import com.example.nanlinkdemo.DB.bean.SceneGroup;

public interface MainModel {
    void getMenu(String nickName);

    void getSceneList();

    void getSceneGroupList();

    void getThreePointMenu(int type, int furtherPosition);

    void deleteScene(Scene scene);

    void addScene(String inputText);

    void addSceneGroup(String inputText);

    void deleteSceneGroup(SceneGroup sceneGroup);

    void queryScene(String inputText);

    void querySceneGroup(String inputText);

}
