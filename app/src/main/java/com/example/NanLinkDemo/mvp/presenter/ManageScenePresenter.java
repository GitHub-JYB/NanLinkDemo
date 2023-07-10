package com.example.NanLinkDemo.mvp.presenter;

import android.view.View;

import com.example.NanLinkDemo.DB.bean.Scene;
import com.example.NanLinkDemo.DB.bean.SceneGroup;

import java.util.List;

public interface ManageScenePresenter {
    void getSceneListFromModel();

    void switchOnclick(View view);

    void switchSceneList(int position);

    void receiveQueryScene(List<Scene> scenes, String sceneGroupName);


    void getSceneGroupFromModel(String sceneGroupName);

    void receiveSceneGroup(List<SceneGroup> sceneGroups);
}
