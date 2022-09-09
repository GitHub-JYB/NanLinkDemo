package com.example.nanlinkdemo.mvp.presenter.Impl;


import static com.example.nanlinkdemo.mvp.adapter.SceneAdapter.TYPE_SCENE;
import static com.example.nanlinkdemo.mvp.adapter.SceneAdapter.TYPE_SCENE_GROUP;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.nanlinkdemo.DB.bean.Scene;
import com.example.nanlinkdemo.DB.bean.SceneGroup;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.mvp.model.Impl.MainModelImpl;
import com.example.nanlinkdemo.mvp.presenter.MainPresenter;
import com.example.nanlinkdemo.mvp.view.MainView;
import com.example.nanlinkdemo.util.Constant;

import java.util.ArrayList;
import java.util.List;

public class MainPresenterImpl implements MainPresenter {

    private final MainView view;
    private final MainModelImpl model;
    private ArrayList<Menu> menuArrayList;
    private ArrayList<String> threePointList;
    private List<Scene> sceneList;
    private List<SceneGroup> sceneGroupList;
    private boolean completeGetScene, completeGetSceneGroup;


    public MainPresenterImpl(MainView mainView) {
        this.view = mainView;
        model = new MainModelImpl(this);
    }

    @Override
    public void getMenuFromModel() {
        model.getMenu();
    }

    @Override
    public void showMenuToView(ArrayList<Menu> menuArrayList) {
        this.menuArrayList = menuArrayList;
        view.showMenu(menuArrayList);
    }

    @Override
    public void menuSwitch(int position) {
        view.closeDrawLayout();
        switch (menuArrayList.get(position).getText()) {
            case "测试者":
                ARouter.getInstance().build(Constant.ACTIVITY_URL_UserSetting).navigation();
                break;
            case "创建场景":
            case "创建场景群组":
                view.showMenuDialog(menuArrayList.get(position).getText(), "", 1);
                break;
            case "排序":
                view.showMenuDialog(menuArrayList.get(position).getText(), "该功能还没开发", 0);
                break;
            case "设置":
                ARouter.getInstance().build(Constant.ACTIVITY_URL_Setting).navigation();
                break;
            case "有关":
                ARouter.getInstance().build(Constant.ACTIVITY_URL_WebView).withInt("contentId", Constant.ABOUT).navigation();
                break;
        }
    }

    @Override
    public void toolbarSwitch(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left_btn:
                view.openDrawLayout();
        }
    }

    @Override
    public void sceneListSwitch(int position) {

    }

    @Override
    public void getSceneListFromModel() {
        completeGetScene = completeGetSceneGroup = false;
        model.getSceneList();
        model.getSceneGroupList();
    }

    @Override
    public void showSceneListToView(List<Scene> sceneList, List<SceneGroup> sceneGroupList) {
        this.sceneList = sceneList;
        this.sceneGroupList = sceneGroupList;
        view.showSceneList(sceneList, sceneGroupList);

    }

    @Override
    public void querySceneToModel(String inputText) {
        model.queryScene(inputText);
    }

    @Override
    public void querySceneGroupToModel(String inputText) {
        model.querySceneGroup(inputText);
    }

    @Override
    public void addSceneToModel(String inputText) {
        model.addScene(inputText);
    }

    @Override
    public void addSceneGroupToModel(String inputText) {
        model.addSceneGroup(inputText);
    }

    @Override
    public void updateSceneListToView() {
        view.updateSceneList();
    }

    @Override
    public void getThreePointMenuFromModel(int type, int position) {
        model.getThreePointMenu(type, position);
    }

    @Override
    public void showThreePointMenuToView(ArrayList<String> threePointList, int type, int position) {
        this.threePointList = threePointList;
        view.showThreePointMenu(threePointList, type, position);
    }

    @Override
    public void switchThreePointMenu(int position, int type, int furtherPosition) {
        switch (threePointList.get(position)){
            case "重命名":
                view.dismissSettingDialog();
                view.showMenuDialog("重命名", "", 1);
                break;
            case "编辑备注":
                view.dismissSettingDialog();
                view.showMenuDialog("编辑备注", "", 1);
                break;
            case "删除":
                view.dismissSettingDialog();

                if (type == TYPE_SCENE_GROUP){
                    view.deleteSceneGroup(furtherPosition - sceneList.size() - 1);

                }else if (type == TYPE_SCENE){
                    view.deleteScene(furtherPosition);

                }
                break;
            case "取消":
                view.dismissSettingDialog();
                break;
        }
    }

    @Override
    public void deleteSceneFromModel(Scene scene) {
        model.deleteScene(scene);
    }

    @Override
    public void completeGetScene(List<Scene> scenes) {
        sceneList = scenes;
        completeGetScene = true;
        if (completeGetSceneGroup){
            showSceneListToView(sceneList, sceneGroupList);
        }
    }

    @Override
    public void completeGetSceneGroup(List<SceneGroup> sceneGroups) {
        sceneGroupList = sceneGroups;
        completeGetSceneGroup = true;
        if (completeGetScene){
            showSceneListToView(sceneList, sceneGroupList);
        }
    }

    @Override
    public void deleteSceneGroupFromModel(SceneGroup sceneGroup) {
        model.deleteSceneGroup(sceneGroup);
    }

    @Override
    public void switchDialog(String inputText, String title) {
        switch (title){
            case "创建场景":
                if (inputText.length() == 0){
                    view.showSnack("请输入场景名称");
                }else {
                    querySceneToModel(inputText);
                    view.dismissMyDialog();
                }
                break;
            case "创建场景群组":
                if (inputText.length() == 0){
                    view.showSnack("请输入场景群组名称");
                }else {
                    querySceneGroupToModel(inputText);
                    view.dismissMyDialog();
                }
                break;

            case "重命名":

        }
    }

    @Override
    public void switchQuerySceneResult(String inputText, List<Scene> scenes) {
        if (scenes.size() == 0){
            model.addScene(inputText);
        }else {
            view.showMenuDialog("创建场景", "该场景名称已存在，请尝试使用其它名称。", 0);
        }
    }

    @Override
    public void switchQuerySceneGroupResult(String inputText, List<SceneGroup> sceneGroups) {
        if (sceneGroups.size() == 0){
            model.addSceneGroup(inputText);
        }else {
            view.showMenuDialog("创建场景群组", "该场景群组名称已存在，请尝试使用其它名称。", 0);
        }
    }

}
