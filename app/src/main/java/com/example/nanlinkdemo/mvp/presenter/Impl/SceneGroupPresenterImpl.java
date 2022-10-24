package com.example.nanlinkdemo.mvp.presenter.Impl;

import android.view.View;

import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.DB.bean.Scene;
import com.example.nanlinkdemo.DB.bean.SceneGroup;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.mvp.adapter.ThreePointAdapter;
import com.example.nanlinkdemo.mvp.model.Impl.SceneGroupModelImpl;
import com.example.nanlinkdemo.mvp.presenter.SceneGroupPresenter;
import com.example.nanlinkdemo.mvp.view.SceneGroupView;
import com.example.nanlinkdemo.ui.MyDialog;
import com.example.nanlinkdemo.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class SceneGroupPresenterImpl implements SceneGroupPresenter {
    private final SceneGroupView view;
    private final SceneGroupModelImpl model;
    private ArrayList<Menu> menuArrayList;
    private List<Scene> sceneList;
    private SceneGroup sceneGroup;

    public SceneGroupPresenterImpl(SceneGroupView view) {
        this.view = view;
        this.model = new SceneGroupModelImpl(this);
    }

    @Override
    public void toolbarSwitch(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left_btn:
                view.finish();
                break;
            case R.id.toolbar_right_btn:
                view.openDrawLayout();
                break;
            case R.id.toolbar_right_second_btn:
                view.showMyDialog(MyDialog.Read_OneBtn_NormalTitle_BlueOneBtn, "搜索", "该功能还没开发", "重试", null);
                break;
        }
    }

    @Override
    public void menuSwitch(int position) {
        view.closeDrawLayout();
        switch (position) {
            case 0:
            case 2:
                view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn, menuArrayList.get(position).getText(), "", "取消", null, "创建", new MyDialog.PositiveOnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (view.getInputTextMyDialog().length() == 0){
                            view.showSnack("请输入场景名称");
                        }else {
                            model.queryScene(view.getInputTextMyDialog());
                            view.dismissMyDialog();
                        }
                    }
                });
                break;
            case 3:
            case 5:
                view.showMyDialog(MyDialog.Read_OneBtn_NormalTitle_BlueOneBtn, menuArrayList.get(position).getText(), "该功能还没开发", "重试", null);
                break;
        }
    }

    @Override
    public void sceneListSwitch(int position) {
        view.showMyDialog(MyDialog.Read_OneBtn_NormalTitle_BlueOneBtn, "场景列表点击", sceneList.get(position).getName(), "重试", null);
    }

    @Override
    public void getMenuFromModel() {
        model.getMenu();
    }

    @Override
    public void getSceneListFromModel(String sceneGroupName) {
        model.getSceneList(sceneGroupName);
    }

    @Override
    public void receiveMenu(ArrayList<Menu> menuArrayList) {
        this.menuArrayList = menuArrayList;
        view.showMenu(menuArrayList);
    }

    @Override
    public void receiveSceneList(List<Scene> sceneList) {
        this.sceneList = sceneList;
        if (!sceneList.isEmpty()){
            view.showSceneList(sceneList);
        }
    }

    @Override
    public void sceneMenuSwitch(int position) {
        model.getThreePointMenu(position);
    }

    @Override
    public void receiveThreePointMenu(ArrayList<String> threePointList, int scenePosition) {
        view.showSettingDialog(threePointList, new ThreePointAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
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
                            view.showMyDialog(MyDialog.Read_TwoBtn_WarningTitle_WarningTwoBtn, threePointList.get(position), "是否要删除该场景?", "取消", null, "删除", new MyDialog.PositiveOnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    view.dismissMyDialog();
//                            view.startLoading();
                                    model.deleteScene(sceneList.get(scenePosition));
                                }
                            });
                        break;
                    case 4:
                        view.dismissSettingDialog();
                        break;
                }
            }
        });
    }

    @Override
    public void updateSceneListToView() {
        view.updateRecycleView();
    }

    @Override
    public void switchQuerySceneResult(String inputText, List<Scene> scenes) {
        if (scenes.size() == 0){
            model.addScene(new Scene(MyApplication.getOnlineUser().getEmail(), inputText, 0, "", DateUtil.getTime(), DateUtil.getTime(), view.getSceneGroupName()));
            sceneGroup.setSceneNum(sceneGroup.getSceneNum() + 1 );
            sceneGroup.setModifiedDate(DateUtil.getTime());
            model.updateSceneGroup(sceneGroup);
        }else {
            view.showMyDialog(MyDialog.Read_OneBtn_NormalTitle_BlueOneBtn, "创建场景", "该场景名称已存在，请尝试使用其它名称。", "重试",null);
        }
    }

    @Override
    public void getSceneGroupFromModel(String sceneGroupName) {
        model.querySceneGroup(sceneGroupName);
    }

    @Override
    public void updateSceneGroup(List<SceneGroup> sceneGroups) {
        sceneGroup = sceneGroups.get(0);
    }
}
