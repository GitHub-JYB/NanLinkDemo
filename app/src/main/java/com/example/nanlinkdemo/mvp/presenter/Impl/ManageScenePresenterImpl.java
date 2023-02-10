package com.example.nanlinkdemo.mvp.presenter.Impl;

import android.view.View;

import com.example.nanlinkdemo.DB.bean.Scene;
import com.example.nanlinkdemo.DB.bean.SceneGroup;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.mvp.model.Impl.ManageSceneModelImpl;
import com.example.nanlinkdemo.mvp.presenter.ManageScenePresenter;
import com.example.nanlinkdemo.mvp.view.ManageSceneView;

import java.util.ArrayList;
import java.util.List;

public class ManageScenePresenterImpl implements ManageScenePresenter {
    private final ManageSceneModelImpl model;
    private final ManageSceneView view;
    private static final int Type_uncheckScene = 0;
    private static final int Type_checkScene = 1;
    private ArrayList<Scene> sceneList;
    private SceneGroup sceneGroup;


    public ManageScenePresenterImpl(ManageSceneView view) {
        this.view = view;
        model = new ManageSceneModelImpl(this);
    }

    @Override
    public void getSceneListFromModel() {
        model.queryScene("", Type_uncheckScene);
    }

    @Override
    public void switchOnclick(View v) {
        switch (v.getId()){
            case R.id.toolbar_left_btn:
                view.finish();
                break;
            case R.id.finish:
                for (Scene scene : sceneList){
                    model.updateScene(scene);
                }
                model.updateSceneGroup(sceneGroup);
                view.finish();
                break;
        }
    }

    @Override
    public void switchSceneList(int position) {
        if (sceneList.get(position).getSceneGroup().isEmpty()){
            sceneList.get(position).setSceneGroup(sceneGroup.getName());
            sceneGroup.setSceneNum(sceneGroup.getSceneNum() + 1);
        }else {
            sceneList.get(position).setSceneGroup("");
            sceneGroup.setSceneNum(sceneGroup.getSceneNum() - 1);
        }
        updateSceneList();
    }

    @Override
    public void receiveQueryScene(List<Scene> scenes, int type) {
        if (type == Type_uncheckScene){
            sceneList = (ArrayList<Scene>) scenes;
            model.queryScene(sceneGroup.getName(), Type_checkScene);
        }else if (type == Type_checkScene){
            sceneList.addAll(scenes);
            sceneGroup.setSceneNum(scenes.size());
            updateSceneList();
        }

    }

    @Override
    public void updateSceneList() {
        view.showScene(sceneList);
    }

    @Override
    public void receiveQuerySceneGroup(List<SceneGroup> sceneGroups) {
        sceneGroup = sceneGroups.get(0);
    }

    @Override
    public void getSceneGroupFromModel(String sceneGroupName) {
        model.querySceneGroup(sceneGroupName);
    }
}
