package com.example.nanlinkdemo.mvp.presenter.Impl;

import android.view.View;

import com.example.nanlinkdemo.Application.MyApplication;
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
    private Scene scene;
    private ArrayList<Menu> sortArrayList;
    private ArrayList<Menu> settingArrayList;

    public ScenePresenterImpl(SceneView view) {
        this.view = view;
        model = new SceneModelImpl(this);
    }

    @Override
    public void getSceneFromModel(String sceneName) {
        model.getSceneList(sceneName);
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
                view.openDrawLayout();
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
                            SnackBarUtil.show(v, "请输入场景群组名称");
                        }else {
                            model.queryScene(view.getInputTextMyDialog(), Type_rename);
                        }
                    }
                });
                break;
            case 3:
                view.closeDrawLayout();
                view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn, settingArrayList.get(position).getText(), scene.getRemark(), "取消", null, "完成", new MyDialog.PositiveOnClickListener() {
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
                view.showMyDialog(MyDialog.Read_OneBtn_WarningTitle_WhiteOneBtn_TwoMessage, settingArrayList.get(position).getText(), "删除该场景群组及群组中的场景", "(该群组中的场景将会被删除)", new MyDialog.MessageOneOnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view.dismissMyDialog();
                        view.showMyDialog(MyDialog.Read_TwoBtn_WarningTitle_WarningTwoBtn, settingArrayList.get(position).getText(), "是否确定要删除该场景群组?", "(该群组中的场景将会被删除)", "取消", null, "删除", new MyDialog.PositiveOnClickListener() {
                            @Override
                            public void onClick(View v) {
                                model.querySceneFromGroup(scene.getName(), Type_delete);
                                model.deleteScene(scene);
                                view.dismissMyDialog();
                                view.finish();
                            }
                        });

                    }
                }, "仅删除场景群组", "(该群组中的场景将会被返回到场景列表中)", new MyDialog.MessageTwoOnClickListener()

                {
                    @Override
                    public void onClick (View v){
                        view.dismissMyDialog();
                        view.showMyDialog(MyDialog.Read_TwoBtn_WarningTitle_WarningTwoBtn, settingArrayList.get(position).getText(), "是否确定要删除该场景群组?", "(该群组中的场景将会返回到场景列表中)", "取消", null, "删除", new MyDialog.PositiveOnClickListener() {
                            @Override
                            public void onClick(View v) {
                                model.querySceneFromGroup(sceneGroup.getName(), Type_update);
                                model.deleteSceneGroup(sceneGroup);
                                view.dismissMyDialog();
                                view.finish();
                            }
                        });
                    }
                }, "取消", null);
                break;

        }
    }

    @Override
    public void getFixtureListFromModel(String sceneName) {

    }

    @Override
    public void getMenuFromModel() {
        model.getMenu();
    }

    @Override
    public void menuSwitch(int position) {
        switch (position){
            case 1:
            case 2:
            case 3:
                model.getSortList(scene.getSortPosition());
                break;
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                model.getSettingList();
                break;
        }
    }

    @Override
    public void FixtureListSwitch(int position) {

    }

    @Override
    public void FixtureMenuSwitch(int position) {

    }

    @Override
    public void receiveMenu(ArrayList<Menu> menuArrayList) {
        this.menuArrayList = menuArrayList;
        view.showMenu(menuArrayList);
    }

    @Override
    public void showSortListToView(ArrayList<Menu> sortArrayList) {
        this.sortArrayList = sortArrayList;
        view.showSortList(sortArrayList);
    }

    @Override
    public void receiveSceneList(List<Scene> scenes) {
        scene = scenes.get(0);
    }

    @Override
    public void showSettingListToView(ArrayList<Menu> settingArrayList) {
        this.settingArrayList = settingArrayList;
        view.showSettingList(settingArrayList);
    }
}
