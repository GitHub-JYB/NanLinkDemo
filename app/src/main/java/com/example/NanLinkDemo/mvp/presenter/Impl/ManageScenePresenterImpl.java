package com.example.NanLinkDemo.mvp.presenter.Impl;

import android.view.View;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.bean.Scene;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.mvp.model.Impl.ManageSceneModelImpl;
import com.example.NanLinkDemo.mvp.presenter.ManageScenePresenter;
import com.example.NanLinkDemo.mvp.view.ManageSceneView;

import java.util.ArrayList;
import java.util.List;

public class ManageScenePresenterImpl implements ManageScenePresenter {
    private final ManageSceneModelImpl model;
    private final ManageSceneView view;
    private ArrayList<Scene> sceneList;


    public ManageScenePresenterImpl(ManageSceneView view) {
        this.view = view;
        model = new ManageSceneModelImpl(this);
    }

    @Override
    public void getSceneListFromModel() {
        model.queryScene();
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
                model.updateSceneGroup(MyApplication.getSceneGroup());
                view.finish();
                break;
        }
    }

    @Override
    public void switchSceneList(int position) {
        if (sceneList.get(position).getSceneGroup().isEmpty()){
            sceneList.get(position).setSceneGroup(MyApplication.getSceneGroup().getName());
            MyApplication.getSceneGroup().setSceneNum(MyApplication.getSceneGroup().getSceneNum() + 1);
        }else {
            sceneList.get(position).setSceneGroup("");
            MyApplication.getSceneGroup().setSceneNum(MyApplication.getSceneGroup().getSceneNum() - 1);
        }
        view.showScene(sceneList);
    }

    @Override
    public void receiveQueryScene(List<Scene> scenes) {
        sceneList = (ArrayList<Scene>) scenes;
        view.showScene(sceneList);
    }

}
