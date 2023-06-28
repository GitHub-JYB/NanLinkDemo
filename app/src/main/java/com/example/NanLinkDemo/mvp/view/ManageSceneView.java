package com.example.NanLinkDemo.mvp.view;

import com.example.NanLinkDemo.DB.bean.Scene;

import java.util.ArrayList;

public interface ManageSceneView {

    void setPresenter();

    void showScene(ArrayList<Scene> sceneList);

    void finish();

}
