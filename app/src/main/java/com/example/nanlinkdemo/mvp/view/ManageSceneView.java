package com.example.nanlinkdemo.mvp.view;

import com.example.nanlinkdemo.DB.bean.Scene;

import java.util.ArrayList;

public interface ManageSceneView {

    void setPresenter();

    void showScene(ArrayList<Scene> sceneList);

    void finish();

}
