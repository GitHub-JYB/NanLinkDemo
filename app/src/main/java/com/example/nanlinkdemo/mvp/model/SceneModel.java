package com.example.nanlinkdemo.mvp.model;

import com.example.nanlinkdemo.DB.bean.Scene;

public interface SceneModel {
    void getMenu();

    void getSortList(int position);

    void getSettingList();

    void updateScene(Scene scene);

    void getSceneList(String sceneName);
}
