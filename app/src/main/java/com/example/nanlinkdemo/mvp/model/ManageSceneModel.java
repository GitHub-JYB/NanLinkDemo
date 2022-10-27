package com.example.nanlinkdemo.mvp.model;

import com.example.nanlinkdemo.DB.bean.Scene;
import com.example.nanlinkdemo.DB.bean.SceneGroup;

public interface ManageSceneModel {
    void queryScene(String sceneGroupName, int type);

    void querySceneGroup(String sceneGroupName);

    void updateScene(Scene scene);

    void updateSceneGroup(SceneGroup sceneGroup);
}
