package com.example.NanLinkDemo.mvp.model;

import com.example.NanLinkDemo.DB.bean.Scene;
import com.example.NanLinkDemo.DB.bean.SceneGroup;

public interface ManageSceneModel {
    void queryScene(String sceneGroupName);

    void updateScene(Scene scene);

    void updateSceneGroup(SceneGroup sceneGroup);

    void getSceneGroup(String sceneGroupName);
}
