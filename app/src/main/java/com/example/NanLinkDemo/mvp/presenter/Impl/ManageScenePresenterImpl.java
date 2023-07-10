package com.example.NanLinkDemo.mvp.presenter.Impl;

import android.view.View;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.FixtureGroup;
import com.example.NanLinkDemo.DB.bean.Scene;
import com.example.NanLinkDemo.DB.bean.SceneGroup;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.mvp.model.Impl.ManageSceneModelImpl;
import com.example.NanLinkDemo.mvp.presenter.ManageScenePresenter;
import com.example.NanLinkDemo.mvp.view.ManageSceneView;
import com.example.NanLinkDemo.util.DateUtil;
import com.example.NanLinkDemo.util.SortUtil;

import java.util.ArrayList;
import java.util.List;

public class ManageScenePresenterImpl implements ManageScenePresenter {
    private final ManageSceneModelImpl model;
    private final ManageSceneView view;
    private ArrayList<Scene> sceneList;
    private ArrayList<Scene> noGroupSceneList = new ArrayList<>();
    private ArrayList<Scene> hasGroupSceneList = new ArrayList<>();

    private SceneGroup sceneGroup;


    public ManageScenePresenterImpl(ManageSceneView view) {
        this.view = view;
        model = new ManageSceneModelImpl(this);
    }

    @Override
    public void getSceneListFromModel() {
        model.queryScene("");
    }

    @Override
    public void switchOnclick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left_btn:
                view.finish();
                break;
            case R.id.finish:
                view.startLoading();
                for (Scene scene : sceneList){
                    model.updateScene(scene);
                }
                sceneGroup.setModifiedDate(DateUtil.getTime());
                model.updateSceneGroup(sceneGroup);
                view.stopLoading();
                view.finish();
                break;
        }
    }

    @Override
    public void switchSceneList(int position) {
        if (sceneList.get(position).getSceneGroupName().equals("")) {
            sceneList.get(position).setSceneGroupName(view.getSceneGroupName());
            sceneGroup.setSceneNum(sceneGroup.getSceneNum() + 1);
        } else {
            sceneList.get(position).setSceneGroupName("");
            sceneGroup.setSceneNum(sceneGroup.getSceneNum() - 1);
        }
        view.showScene(sceneList);
    }

    @Override
    public void receiveQueryScene(List<Scene> scenes, String sceneGroupName) {
        if (sceneGroupName.equals("")) {
            noGroupSceneList = (ArrayList<Scene>) scenes;
            sceneList = SortUtil.sortSceneList(noGroupSceneList, MyApplication.getOnlineUser().getSortPosition());
            if (!view.getSceneGroupName().equals("")) {
                model.queryScene(view.getSceneGroupName());
            }
        } else {
            hasGroupSceneList = SortUtil.sortSceneList((ArrayList<Scene>) scenes, MyApplication.getOnlineUser().getSortPosition());
            for (int i = hasGroupSceneList.size() - 1; i >= 0; i--) {
                sceneList.add(0, hasGroupSceneList.get(i));
            }
        }
        view.showScene(sceneList);
    }

    @Override
    public void getSceneGroupFromModel(String sceneGroupName) {
        model.getSceneGroup(sceneGroupName);
    }

    @Override
    public void receiveSceneGroup(List<SceneGroup> sceneGroups) {
        if (!sceneGroups.isEmpty()) {
            sceneGroup = sceneGroups.get(0);
        }
    }

}
