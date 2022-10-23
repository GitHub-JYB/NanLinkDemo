package com.example.nanlinkdemo.mvp.presenter.Impl;


import static com.example.nanlinkdemo.mvp.adapter.SceneAdapter.TYPE_SCENE;
import static com.example.nanlinkdemo.mvp.adapter.SceneAdapter.TYPE_SCENE_GROUP;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.DB.bean.Scene;
import com.example.nanlinkdemo.DB.bean.SceneGroup;
import com.example.nanlinkdemo.DB.bean.User;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.mvp.model.Impl.MainModelImpl;
import com.example.nanlinkdemo.mvp.presenter.MainPresenter;
import com.example.nanlinkdemo.mvp.view.MainView;
import com.example.nanlinkdemo.ui.MyDialog;
import com.example.nanlinkdemo.util.Constant;
import com.example.nanlinkdemo.util.SnackBarUtil;

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
        model.getMenu(MyApplication.getOnlineUser().getNickName());
    }

    @Override
    public void showMenuToView(ArrayList<Menu> menuArrayList) {
        this.menuArrayList = menuArrayList;
        view.showMenu(menuArrayList);
    }

    @Override
    public void menuSwitch(int position) {
        view.closeDrawLayout();
        switch (position) {
            case 1:
                ARouter.getInstance().build(Constant.ACTIVITY_URL_UserSetting).navigation();
                break;
            case 3:
            case 4:
                view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn, menuArrayList.get(position).getText(), "", "取消", null, "创建", new MyDialog.PositiveOnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switchDialog(view.getInputTextMyDialog(), menuArrayList.get(position).getText());
                    }
                });
                break;
            case 5:
                view.showMyDialog(MyDialog.Read_OneBtn_NormalTitle_BlueOneBtn, menuArrayList.get(position).getText(), "该功能还没开发", "重试",null);
                break;
            case 7:
                ARouter.getInstance().build(Constant.ACTIVITY_URL_Setting).navigation();
                break;
            case 8:
                ARouter.getInstance().build(Constant.ACTIVITY_URL_WebView).withInt("contentId", Constant.ABOUT).navigation();
                break;
        }
    }

    @Override
    public void toolbarSwitch(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left_btn:
                view.openDrawLayout();
                break;
            case R.id.toolbar_right_btn:
                view.showMyDialog(MyDialog.Read_OneBtn_NormalTitle_BlueOneBtn, "搜索", "该功能还没开发", "重试", null);
                break;
        }
    }

    @Override
    public void sceneListSwitch(int position) {
        if (sceneList.size() == 0){
            if (position != 0){
                view.showMyDialog(MyDialog.Read_OneBtn_NormalTitle_BlueOneBtn, "场景列表点击", sceneGroupList.get(position - 1).getName(), "重试", null);
            }
        }else {
            if (position < sceneList.size()){
                view.showMyDialog(MyDialog.Read_OneBtn_NormalTitle_BlueOneBtn, "场景列表点击", sceneList.get(position).getName(), "重试", null);
            }else if (position > sceneList.size()){
                view.showMyDialog(MyDialog.Read_OneBtn_NormalTitle_BlueOneBtn, "场景列表点击", sceneGroupList.get(position - sceneList.size() - 1).getName(), "重试", null);
            }
        }

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
    public void updateSceneListToView() {
        view.updateRecycleView();
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
        switch (position){
            case 1:
                view.dismissSettingDialog();
                view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn, threePointList.get(position), "", "取消", null, "重命名", new MyDialog.PositiveOnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        switchDialog(view.getInputTextMyDialog(), menuArrayList.get(position).getText());
                    }
                });
                break;
            case 2:
                view.dismissSettingDialog();
                view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn, threePointList.get(position), "", "取消", null, "完成", new MyDialog.PositiveOnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        switchDialog(view.getInputTextMyDialog(), menuArrayList.get(position).getText());
                    }
                });
                break;
            case 3:
                view.dismissSettingDialog();

                if (type == TYPE_SCENE_GROUP){
                    view.showMyDialog(MyDialog.Read_TwoBtn_WarningTitle_WarningTwoBtn, threePointList.get(position), "是否要删除该场景?", "取消", null, "删除", new MyDialog.PositiveOnClickListener() {
                        @Override
                        public void onClick(View v) {
                            view.dismissMyDialog();
                            view.deleteSceneGroup(furtherPosition - sceneList.size() - 1);
                        }
                    });

                }else if (type == TYPE_SCENE){
                    view.showMyDialog(MyDialog.Read_TwoBtn_WarningTitle_WarningTwoBtn, threePointList.get(position), "是否要删除该场景?", "取消", null, "删除", new MyDialog.PositiveOnClickListener() {
                        @Override
                        public void onClick(View v) {
                            view.dismissMyDialog();
//                            view.startLoading();
                            view.deleteScene(furtherPosition);
                        }
                    });

                }
                break;
            case 4:
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
            view.showMyDialog(MyDialog.Read_OneBtn_NormalTitle_BlueOneBtn, "创建场景", "该场景名称已存在，请尝试使用其它名称。", "重试",null);
        }
    }

    @Override
    public void switchQuerySceneGroupResult(String inputText, List<SceneGroup> sceneGroups) {
        if (sceneGroups.size() == 0){
            model.addSceneGroup(inputText);
        }else {
            view.showMyDialog(MyDialog.Read_OneBtn_NormalTitle_BlueOneBtn, "创建场景群组", "该场景群组名称已存在，请尝试使用其它名称。", "重试",null);
        }
    }

}
