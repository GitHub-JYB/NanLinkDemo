package com.example.nanlinkdemo.mvp.presenter.Impl;

import android.view.View;

import com.example.nanlinkdemo.DB.bean.Fixture;
import com.example.nanlinkdemo.DB.bean.FixtureGroup;
import com.example.nanlinkdemo.DB.bean.Scene;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.mvp.model.Impl.SceneModelImpl;
import com.example.nanlinkdemo.mvp.presenter.ScenePresenter;
import com.example.nanlinkdemo.mvp.view.SceneView;
import com.example.nanlinkdemo.ui.MyDialog;
import com.example.nanlinkdemo.util.DateUtil;
import com.example.nanlinkdemo.util.SnackBarUtil;

import java.util.ArrayList;
import java.util.List;

public class ScenePresenterImpl implements ScenePresenter {
    private final SceneView view;
    private final SceneModelImpl model;
    private ArrayList<Menu> menuArrayList;

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    private Scene scene;
    private ArrayList<Menu> sortArrayList;
    private ArrayList<ArrayList<Fixture>> fixtureListInGroup;

    private ArrayList<Fixture> fixtureList;
    private ArrayList<FixtureGroup> fixtureGroupList;


    private ArrayList<Menu> settingArrayList;
    private static final int Type_add = 0;
    private static final int Type_rename = 1;
    private static final int Type_init = 2;
    private static final int Type_init_noGroup = 3;
    private static final int Type_init_inGroup = 4;




    public ScenePresenterImpl(SceneView view) {
        this.view = view;
        model = new SceneModelImpl(this);
    }

    @Override
    public void getSceneFromModel(String sceneName) {
        model.queryScene(sceneName, Type_init);
    }

    @Override
    public void switchOnclick(View v) {
        switch (v.getId()){
            case R.id.toolbar_left_btn:
                view.showMyDialog(MyDialog.Read_TwoBtn_NormalTitle_WhiteTwoBtn, "退出场景", "是否要退出该场景?", "取消", null, "退出", new MyDialog.PositiveOnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view.dismissMyDialog();
                        view.finish();
                    }
                });
                break;
            case R.id.add_fixture:
                break;
            case R.id.toolbar_right_btn:
                view.initMenu();
                break;
        }
    }

    @Override
    public void sortSwitch(int position) {
        switch (position){
            case 0:
                view.initMenu();
                break;
            case 1:
                break;
            default:
                scene.setSortPosition(position - 2);
                model.updateScene(scene);
                model.getSortList(position - 2);
                view.initMenu();
                break;
        }
    }

    @Override
    public void settingSwitch(int position) {
        switch (position){
            case 0:
                view.initMenu();
                break;
            case 2:
                view.closeDrawLayout();
                view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn, settingArrayList.get(position).getText(), scene.getName(), "取消", null, "重命名", new MyDialog.PositiveOnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (view.getInputTextMyDialog().isEmpty()){
                            SnackBarUtil.show(v, "请输入场景名称");
                        }else {
                            model.queryScene(view.getInputTextMyDialog(), Type_rename);
                        }
                    }
                });
                break;
            case 3:
                view.closeDrawLayout();
                view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn_Remark, settingArrayList.get(position).getText(), scene.getRemark(), "取消", null, "完成", new MyDialog.PositiveOnClickListener() {
                    @Override
                    public void onClick(View v) {
                        scene.setRemark(view.getInputTextMyDialog());
                        scene.setModifiedDate(DateUtil.getTime());
                        model.updateScene(scene);
                        view.dismissMyDialog();
                    }
                });
                break;
            case 4:
                view.closeDrawLayout();
                view.showMyDialog(MyDialog.Read_TwoBtn_WarningTitle_WarningTwoBtn, settingArrayList.get(position).getText(), "是否要删除该场景?", "取消", null, "删除", new MyDialog.PositiveOnClickListener() {
                    @Override
                    public void onClick(View v) {
                        model.deleteScene(scene);
                        for (FixtureGroup fixtureGroup : fixtureGroupList){
                            model.deleteFixtureGroup(fixtureGroup);
                        }
                        fixtureGroupList = new ArrayList<FixtureGroup>();
                        for (Fixture fixture : fixtureList){
                            model.deleteFixture(fixture);
                        }
                        fixtureList = new ArrayList<Fixture>();
                        view.dismissMyDialog();
                        view.finish();
                    }
                });
                break;

        }
    }

    @Override
    public void getFixtureListFromModel() {
        model.queryAllFixtureGroup(Type_init);
    }

    @Override
    public void getMenuFromModel() {
        model.getMenu();
    }

    @Override
    public void menuSwitch(int position) {
        switch (position){
            case 1:
                view.closeDrawLayout();
                view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn, menuArrayList.get(position).getText(), "", "取消", null, "创建", new MyDialog.PositiveOnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (view.getInputTextMyDialog().isEmpty()){
                            SnackBarUtil.show(v, "请输入地址码");
                        }else {
                            model.queryFixture(view.getInputTextMyDialog(), Type_add);
                        }
                    }
                });
                break;
            case 2:
                view.closeDrawLayout();
                view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn, menuArrayList.get(position).getText(), "", "取消", null, "创建", new MyDialog.PositiveOnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (view.getInputTextMyDialog().isEmpty()){
                            SnackBarUtil.show(v, "请输入设备群组名称");
                        }else {
                            model.queryFixtureGroup(view.getInputTextMyDialog(), Type_add);
                        }
                    }
                });
                break;
            case 3:
                model.getSortList(scene.getSortPosition());
                break;
            case 5:
            case 7:
            case 8:
            case 10:
                view.closeDrawLayout();
                break;
            case 12:
                model.getSettingList();
                break;
        }
    }

    @Override
    public void FixtureListSwitch(int position) {
        if (fixtureGroupList.isEmpty()){
            if (position > 0){
                view.showMyDialog(MyDialog.Read_OneBtn_NormalTitle_WhiteOneBtn, "点击设备群组: " + fixtureGroupList.get(position - 1).getName(), "该功能还没开发", "重试", null);
            }
        }else {
            if (position > 0 && position <= fixtureGroupList.size()) {
                view.showMyDialog(MyDialog.Read_OneBtn_NormalTitle_WhiteOneBtn, "点击设备群组: " + fixtureGroupList.get(position - 1).getName(), "该功能还没开发", "重试", null);
            } else if (position > fixtureGroupList.size() + 1) {
                view.showMyDialog(MyDialog.Read_OneBtn_NormalTitle_WhiteOneBtn, "点击设备: " + fixtureGroupList.get(position - fixtureGroupList.size() - 2).getName(), "该功能还没开发", "重试", null);

            }
        }
    }

    @Override
    public void FixtureMenuSwitch(int position) {

    }

    @Override
    public void receiveMenu(ArrayList<Menu> menuArrayList) {
        this.menuArrayList = menuArrayList;
        view.showMenu(menuArrayList);
        view.openDrawLayout();
    }

    @Override
    public void showSortListToView(ArrayList<Menu> sortArrayList) {
        this.sortArrayList = sortArrayList;
        view.showSortList(sortArrayList);
    }

    @Override
    public void receiveSceneList(List<Scene> scenes, int type) {
        if (type == Type_init){
            setScene(scenes.get(0));
            getFixtureListFromModel();
        }else if (type == Type_rename){
            if (scenes.isEmpty()){
                scene.setName(view.getInputTextMyDialog());
                scene.setModifiedDate(DateUtil.getTime());
                model.updateScene(scene);
                view.setTitle(scene.getName());
                for (FixtureGroup fixtureGroup : fixtureGroupList){
                    fixtureGroup.setSceneName(scene.getName());
                    model.updateFixtureGroup(fixtureGroup);
                }
                for (Fixture fixture : fixtureList){
                    fixture.setSceneName(scene.getName());
                    model.updateFixture(fixture);
                }
                view.dismissMyDialog();
            }else {
                view.dismissMyDialog();
                view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn, "重命名", scene.getName(), "取消", null, "重命名", new MyDialog.PositiveOnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (view.getInputTextMyDialog().isEmpty()){
                            SnackBarUtil.show(v, "请输入场景名称");
                        }else {
                            model.queryScene(view.getInputTextMyDialog(), Type_rename);
                        }
                    }
                });
            }
        }
    }

    @Override
    public void showSettingListToView(ArrayList<Menu> settingArrayList) {
        this.settingArrayList = settingArrayList;
        view.showSettingList(settingArrayList);
    }

    @Override
    public void receiveAllFixture(List<Fixture> fixtures, int type) {
        if (type == Type_init){
            fixtureList = (ArrayList<Fixture>) fixtures;
        }
        view.showFixtureList(fixtureGroupList, fixtureList);
    }

    @Override
    public void receiveFixtureGroup(List<FixtureGroup> fixtureGroups, int type) {
        if (type == Type_init){
            fixtureGroupList = (ArrayList<FixtureGroup>) fixtureGroups;
            model.queryAllFixture(type);
        }
        if (type == Type_add){
            if (fixtureGroups.isEmpty()){
                model.addFixtureGroup(view.getInputTextMyDialog());
                view.dismissMyDialog();
            }else {
                view.dismissMyDialog();
                view.showMyDialog(MyDialog.Read_OneBtn_NormalTitle_BlueOneBtn, "创建设备群组", "该创建设备群组名称已存在，请尝试使用其它名称。", "重试", new MyDialog.NeutralOnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view.dismissMyDialog();
                        view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn, "创建设备群组", "", "取消", null, "创建", new MyDialog.PositiveOnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (view.getInputTextMyDialog().isEmpty()){
                                    SnackBarUtil.show(v, "请输入设备群组名称");
                                }else {
                                    model.queryFixtureGroup(view.getInputTextMyDialog(), Type_add);
                                }
                            }
                        });
                    }
                });
            }
        }
    }

    @Override
    public void updateFixtureList() {
        getFixtureListFromModel();
    }

    @Override
    public void receiveFixtureList(List<Fixture> fixtures, int type) {
        if (type == Type_add){
            if (fixtures.isEmpty()){
                model.addFixture(view.getInputTextMyDialog());
                view.dismissMyDialog();
                scene.setFixtureNum(scene.getFixtureNum() + 1);
                scene.setModifiedDate(DateUtil.getTime());
                model.updateScene(scene);
            }else {
                view.dismissMyDialog();
                view.showMyDialog(MyDialog.Read_OneBtn_NormalTitle_BlueOneBtn, "创建设备", "该创建设备群组名称已存在，请尝试使用其它名称。", "重试", new MyDialog.NeutralOnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view.dismissMyDialog();
                        view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn, "创建设备", "", "取消", null, "创建", new MyDialog.PositiveOnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (view.getInputTextMyDialog().isEmpty()){
                                    SnackBarUtil.show(v, "请输入地址码");
                                }else {
                                    model.queryFixture(view.getInputTextMyDialog(), Type_add);
                                }
                            }
                        });
                    }
                });
            }
        }

    }

}
