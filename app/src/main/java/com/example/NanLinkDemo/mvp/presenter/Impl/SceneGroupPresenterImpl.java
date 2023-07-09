package com.example.NanLinkDemo.mvp.presenter.Impl;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.bean.Scene;
import com.example.NanLinkDemo.DB.bean.SceneGroup;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.bean.Menu;
import com.example.NanLinkDemo.mvp.adapter.ThreePointAdapter;
import com.example.NanLinkDemo.mvp.model.Impl.SceneGroupModelImpl;
import com.example.NanLinkDemo.mvp.presenter.SceneGroupPresenter;
import com.example.NanLinkDemo.mvp.view.SceneGroupView;
import com.example.NanLinkDemo.ui.MyDialog;
import com.example.NanLinkDemo.util.Constant;
import com.example.NanLinkDemo.util.DateUtil;
import com.example.NanLinkDemo.util.SnackBarUtil;

import java.util.ArrayList;
import java.util.List;

public class SceneGroupPresenterImpl implements SceneGroupPresenter {
    private final SceneGroupView view;
    private final SceneGroupModelImpl model;
    private ArrayList<Menu> menuArrayList;
    private List<Scene> sceneList;
    private SceneGroup sceneGroup;
    private static final int Type_add = 0;
    private static final int Type_rename = 1;
    private static final int Type_int = 2;
    private static final int Type_delete = 3;
    private static final int Type_update = 4;

    private int scenePosition;
    private ArrayList<Menu> sortArrayList;
    private ArrayList<Menu> settingArrayList;

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
                view.initMenu();
                view.openDrawLayout();
                break;
            case R.id.toolbar_right_second_btn:
                view.showMyDialog(MyDialog.Read_OneBtn_NormalTitle_BlueOneBtn, "搜索", "该功能还没开发", "重试", null);
                break;
        }
    }

    @Override
    public void menuSwitch(int position) {
        switch (position) {
            case 1:
                ARouter.getInstance().build(Constant.ACTIVITY_URL_ManageScene).navigation();
                view.closeDrawLayout();
                break;
            case 3:
                view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn, menuArrayList.get(position).getText(), "", "取消", null, "创建", new MyDialog.PositiveOnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (view.getInputTextMyDialog().isEmpty()){
                            SnackBarUtil.show(v, "请输入场景名称");
                        }else {
                            model.queryScene(view.getInputTextMyDialog(), Type_add);
                        }
                    }
                });
                view.closeDrawLayout();
                break;
            case 4:
                model.getSortList();
                break;
            case 6:
                model.getSettingList();
                break;
        }
    }

    @Override
    public void sceneListSwitch(int position) {
        MyApplication.setScene(sceneList.get(position));
        ARouter.getInstance().build(Constant.ACTIVITY_URL_Scene).navigation();
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
        view.showSceneList(sceneList);
    }

    @Override
    public void sceneMenuSwitch(int position) {
        scenePosition = position;
        model.getThreePointMenu();
    }

    @Override
    public void receiveThreePointMenu(ArrayList<String> threePointList) {
        view.showSettingDialog(threePointList, new ThreePointAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                view.dismissSettingDialog();
                switch (position){
                    case 1:
                        view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn, threePointList.get(position), sceneList.get(scenePosition).getName(), "取消", null, "重命名", new MyDialog.PositiveOnClickListener() {
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
                    case 2:
                        view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn_Remark, threePointList.get(position), sceneList.get(scenePosition).getRemark(), "取消", null, "完成", new MyDialog.PositiveOnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sceneList.get(scenePosition).setRemark(view.getInputTextMyDialog());
                                sceneList.get(scenePosition).setModifiedDate(DateUtil.getTime());
                                model.updateScene(sceneList.get(scenePosition));
                                view.dismissMyDialog();
                            }
                        });
                        break;
                    case 3:
                            view.showMyDialog(MyDialog.Read_TwoBtn_WarningTitle_WarningTwoBtn, threePointList.get(position), "是否要删除该场景?", "取消", null, "删除", new MyDialog.PositiveOnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    model.deleteScene(sceneList.get(scenePosition));
                                    sceneGroup.setSceneNum(sceneGroup.getSceneNum() - 1);
                                    sceneGroup.setModifiedDate(DateUtil.getTime());
                                    model.updateSceneGroup(sceneGroup);
                                    view.dismissMyDialog();
                                }
                            });
                        break;
                    case 4:
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
    public void switchQuerySceneResult(List<Scene> scenes, int type) {
        if (type == Type_add){
            if (scenes.isEmpty()){
                model.addScene(new Scene(MyApplication.getOnlineUser().getEmail(), view.getInputTextMyDialog(), 0, "", DateUtil.getTime(), DateUtil.getTime(), sceneGroup.getName()));
                sceneGroup.setSceneNum(sceneGroup.getSceneNum() + 1 );
                sceneGroup.setModifiedDate(DateUtil.getTime());
                model.updateSceneGroup(sceneGroup);
                view.dismissMyDialog();
            }else {
                view.dismissMyDialog();
                view.showMyDialog(MyDialog.Read_OneBtn_NormalTitle_BlueOneBtn, "创建场景", "该场景名称已存在，请尝试使用其它名称。", "重试", new MyDialog.NeutralOnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view.dismissMyDialog();
                        view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn, "创建场景", "", "取消", null, "创建", new MyDialog.PositiveOnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (view.getInputTextMyDialog().isEmpty()){
                                    SnackBarUtil.show(v, "请输入场景名称");
                                }else {
                                    model.queryScene(view.getInputTextMyDialog(), Type_add);
                                }
                            }
                        });
                    }
                });
            }
        }else if (type == Type_rename){
            if (scenes.isEmpty()){
                sceneList.get(scenePosition).setName(view.getInputTextMyDialog());
                sceneList.get(scenePosition).setModifiedDate(DateUtil.getTime());
                model.updateScene(sceneList.get(scenePosition));
                view.dismissMyDialog();
            }else {
                view.dismissMyDialog();
                view.showMyDialog(MyDialog.Read_OneBtn_NormalTitle_BlueOneBtn, "重命名", "该场景名称已存在，请尝试使用其它名称。", "重试", new MyDialog.NeutralOnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view.dismissMyDialog();
                        view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn, "重命名", sceneList.get(scenePosition).getName(), "取消", null, "重命名", new MyDialog.PositiveOnClickListener() {
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
                });
            }
        }


    }

    @Override
    public void getSceneGroupFromModel(String sceneGroupName) {
        model.querySceneGroup(sceneGroupName, Type_int);
    }

    @Override
    public void receiveSceneGroup(List<SceneGroup> sceneGroups, int type) {
        if (type == Type_int){
            sceneGroup = sceneGroups.get(0);
        }else if (type == Type_rename){
            if (sceneGroups.isEmpty()){
                sceneGroup.setName(view.getInputTextMyDialog());
                sceneGroup.setModifiedDate(DateUtil.getTime());
                view.setTitle(sceneGroup.getName());
                model.updateSceneGroup(sceneGroup);
                view.dismissMyDialog();
            }else {
                view.dismissMyDialog();
                view.showMyDialog(MyDialog.Read_OneBtn_NormalTitle_BlueOneBtn, "重命名", "该场景群组名称已存在，请尝试使用其它名称。", "重试", new MyDialog.NeutralOnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view.dismissMyDialog();
                        view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn, "重命名", sceneGroup.getName(), "取消", null, "重命名", new MyDialog.PositiveOnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (view.getInputTextMyDialog().isEmpty()){
                                    SnackBarUtil.show(v, "请输入场景群组名称");
                                }else {
                                    model.querySceneGroup(view.getInputTextMyDialog(), Type_rename);
                                }
                            }
                        });
                    }
                });


            }
        }
    }

    @Override
    public void showSortListToView(ArrayList<Menu> sortArrayList) {
        sortArrayList.get(MyApplication.getOnlineUser().getSortPosition() + 2).setIconResId(R.drawable.ic_selected);
        this.sortArrayList = sortArrayList;
        view.showSortList(sortArrayList);
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
                if (MyApplication.getOnlineUser().getSortPosition() != position - 2){
                    sortArrayList.get(MyApplication.getOnlineUser().getSortPosition() + 2).setIconResId(R.drawable.ic_unselected);
                    MyApplication.getOnlineUser().setSortPosition(position - 2);
                    sortArrayList.get(position).setIconResId(R.drawable.ic_selected);
                    model.updateUser(MyApplication.getOnlineUser());
                    view.showMenu(sortArrayList);
                }
                view.updateRecycleView();
                break;
        }
    }

    @Override
    public void showSettingListToView(ArrayList<Menu> settingArrayList) {
        this.settingArrayList = settingArrayList;
        view.showSettingList(settingArrayList);
    }

    @Override
    public void settingSwitch(int position) {
        switch (position){
            case 0:
                view.initMenu();
                break;
            case 2:
                view.closeDrawLayout();
                view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn, settingArrayList.get(position).getText(), sceneGroup.getName(), "取消", null, "重命名", new MyDialog.PositiveOnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (view.getInputTextMyDialog().isEmpty()){
                            SnackBarUtil.show(v, "请输入场景群组名称");
                        }else {
                            model.querySceneGroup(view.getInputTextMyDialog(), Type_rename);
                        }
                    }
                });
                break;
            case 3:
                view.closeDrawLayout();
                view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn_Remark, settingArrayList.get(position).getText(), sceneGroup.getRemark(), "取消", null, "完成", new MyDialog.PositiveOnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sceneGroup.setRemark(view.getInputTextMyDialog());
                        sceneGroup.setModifiedDate(DateUtil.getTime());
                        model.updateSceneGroup(sceneGroup);
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
                                model.querySceneFromGroup(sceneGroup.getName(), Type_delete);
                                model.deleteSceneGroup(sceneGroup);
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
    public void receiveQuerySceneFromSceneGroup(List<Scene> scenes, int type) {
        if (type == Type_delete){
            for (Scene scene : scenes){
                model.deleteScene(scene);
            }
        }else if (type == Type_update){
            for (Scene scene : scenes){
                scene.setSceneGroup("");
                model.updateScene(scene);
            }
        }
    }
}
