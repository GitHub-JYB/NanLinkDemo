package com.example.NanLinkDemo.mvp.presenter.Impl;


import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.bean.Device;
import com.example.NanLinkDemo.DB.bean.Scene;
import com.example.NanLinkDemo.DB.bean.SceneGroup;
import com.example.NanLinkDemo.DB.bean.User;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.bean.DeviceMessage;
import com.example.NanLinkDemo.bean.Menu;
import com.example.NanLinkDemo.mvp.adapter.ThreePointAdapter;
import com.example.NanLinkDemo.mvp.model.Impl.MainModelImpl;
import com.example.NanLinkDemo.mvp.presenter.MainPresenter;
import com.example.NanLinkDemo.mvp.view.MainView;
import com.example.NanLinkDemo.ui.MyDialog;
import com.example.NanLinkDemo.util.Constant;
import com.example.NanLinkDemo.util.DateUtil;
import com.example.NanLinkDemo.util.SnackBarUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainPresenterImpl implements MainPresenter {

    private final MainView view;
    private final MainModelImpl model;
    private ArrayList<Menu> menuArrayList;
    private List<Scene> sceneList;
    private List<SceneGroup> sceneGroupList;
    private boolean completeGetScene, completeGetSceneGroup;
    private static final int Type_add = 0;
    private static final int Type_rename = 1;
    private static final int Type_delete = 2;
    private static final int Type_update = 3;
    private int scenePosition;
    private ArrayList<Menu> sortArrayList;


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
        switch (position) {
            case 2:
                ARouter.getInstance().build(Constant.ACTIVITY_URL_UserSetting).navigation();
                view.closeDrawLayout();
                break;
            case 4:
                view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn, menuArrayList.get(position).getText(), "", "取消", null, "创建", new MyDialog.PositiveOnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (view.getInputTextMyDialog().isEmpty()){
                            SnackBarUtil.show(v, "请输入场景名称");
                        }else {
                            model.queryScene(view.getInputTextMyDialog(), Type_add);
                            view.dismissMyDialog();
                        }
                    }
                });
                view.closeDrawLayout();
                break;
            case 5:
                view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn, menuArrayList.get(position).getText(), "", "取消", null, "创建", new MyDialog.PositiveOnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (view.getInputTextMyDialog().isEmpty()){
                            SnackBarUtil.show(v, "请输入场景群组名称");
                        }else {
                            model.querySceneGroup(view.getInputTextMyDialog(), Type_add);
                            view.dismissMyDialog();
                        }
                    }
                });
                view.closeDrawLayout();
                break;
            case 6:
                model.getSortList();
                break;
            case 8:
                ARouter.getInstance().build(Constant.ACTIVITY_URL_Setting).navigation();
                view.closeDrawLayout();
                break;
            case 9:
                ARouter.getInstance().build(Constant.ACTIVITY_URL_WebView).withInt("contentId", Constant.ABOUT).navigation();
                view.closeDrawLayout();
                break;
        }
    }

    @Override
    public void toolbarSwitch(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left_btn:
                view.openDrawLayout();
                view.initMenu();
                break;
            case R.id.toolbar_right_btn:
                view.showMyDialog(MyDialog.Read_OneBtn_NormalTitle_BlueOneBtn, "搜索", "该功能还没开发", "重试", null);
                break;
        }
    }

    @Override
    public void sceneListSwitch(int position) {
        if ((sceneList.size() == 0 & position != 0) || (sceneList.size() != 0 & position > sceneList.size())){
            MyApplication.setSceneGroup(sceneGroupList.get(position - sceneList.size() - 1));
            ARouter.getInstance().build(Constant.ACTIVITY_URL_SceneGroup).navigation();
        }
        if (sceneList.size() != 0 & position < sceneList.size()){
            MyApplication.setScene(sceneList.get(position));
            ARouter.getInstance().build(Constant.ACTIVITY_URL_Scene).navigation();
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
    public void updateSceneListToView() {
        view.updateRecycleView();
    }


    @Override
    public void receiveThreePointMenu(ArrayList<String> threePointList) {
        view.showSettingDialog(threePointList, new ThreePointAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                view.dismissSettingDialog();
                switch (position){
                    case 1:
                        if ((sceneList.size() == 0 & scenePosition != 0) || (sceneList.size() != 0 & scenePosition > sceneList.size())){
                            view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn, threePointList.get(position), sceneGroupList.get(scenePosition - sceneList.size() - 1).getName(), "取消", null, "重命名", new MyDialog.PositiveOnClickListener() {
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
                        if (sceneList.size() != 0 & scenePosition < sceneList.size()){
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
                        }

                        break;
                    case 2:
                        if ((sceneList.size() == 0 & scenePosition != 0) || (sceneList.size() != 0 & scenePosition > sceneList.size())){
                            view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn_Remark, threePointList.get(position), sceneGroupList.get(scenePosition - sceneList.size() - 1).getRemark(), "取消", null, "完成", new MyDialog.PositiveOnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    sceneGroupList.get(scenePosition - sceneList.size() - 1).setRemark(view.getInputTextMyDialog());
                                    sceneGroupList.get(scenePosition - sceneList.size() - 1).setModifiedDate(DateUtil.getTime());
                                    model.updateSceneGroup(sceneGroupList.get(scenePosition - sceneList.size() - 1));
                                    view.dismissMyDialog();

                                }
                            });
                        }
                        if (sceneList.size() != 0 & scenePosition < sceneList.size()){
                            view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn_Remark, threePointList.get(position), sceneList.get(scenePosition).getRemark(), "取消", null, "完成", new MyDialog.PositiveOnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    sceneList.get(scenePosition).setRemark(view.getInputTextMyDialog());
                                    sceneList.get(scenePosition).setModifiedDate(DateUtil.getTime());
                                    model.updateScene(sceneList.get(scenePosition));
                                    view.dismissMyDialog();

                                }
                            });
                        }
                        break;
                    case 3:
                        if ((sceneList.size() == 0 & scenePosition != 0) || (sceneList.size() != 0 & scenePosition > sceneList.size())){
                            view.showMyDialog(MyDialog.Read_OneBtn_WarningTitle_WhiteOneBtn_TwoMessage, threePointList.get(position), "删除该场景群组及群组中的场景", "(该群组中的场景将会被删除)", new MyDialog.MessageOneOnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    view.dismissMyDialog();
                                    view.showMyDialog(MyDialog.Read_TwoBtn_WarningTitle_WarningTwoBtn, threePointList.get(position), "是否确定要删除该场景群组?", "(该群组中的场景将会被删除)", "取消", null, "删除", new MyDialog.PositiveOnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            model.deleteSceneGroup(sceneGroupList.get(scenePosition - sceneList.size() - 1));
                                            model.querySceneFromGroup(sceneGroupList.get(scenePosition - sceneList.size() -1).getName(), Type_delete);
                                            view.dismissMyDialog();
                                        }
                                    });

                                }
                            }, "仅删除场景群组", "(该群组中的场景将会被返回到场景列表中)", new MyDialog.MessageTwoOnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    view.dismissMyDialog();
                                    view.showMyDialog(MyDialog.Read_TwoBtn_WarningTitle_WarningTwoBtn, threePointList.get(position), "是否确定要删除该场景群组?", "(该群组中的场景将会返回到场景列表中)", "取消", null, "删除", new MyDialog.PositiveOnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            model.deleteSceneGroup(sceneGroupList.get(scenePosition - sceneList.size() - 1));
                                            model.querySceneFromGroup(sceneGroupList.get(scenePosition - sceneList.size() -1).getName(), Type_update);
                                            view.dismissMyDialog();
                                        }
                                    });
                                }
                            }, "取消", null);
                        }
                        if (sceneList.size() != 0 & scenePosition < sceneList.size()){
                            view.showMyDialog(MyDialog.Read_TwoBtn_WarningTitle_WarningTwoBtn, threePointList.get(position), "是否要删除该场景?", "取消", null, "删除", new MyDialog.PositiveOnClickListener() {
                                @Override
                                public void onClick(View v) {
//                            view.startLoading();
                                    model.deleteScene(sceneList.get(scenePosition));
                                    view.dismissMyDialog();

                                }
                            });
                        }
                        break;
                    case 4:
                        break;
                }
            }
        });
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
    public void switchQuerySceneResult(List<Scene> scenes, int type) {
        if (type == Type_add){
            if (scenes.size() == 0){
                model.addScene(new Scene(MyApplication.getOnlineUser().getEmail(), view.getInputTextMyDialog(), 0, "", DateUtil.getTime(), DateUtil.getTime(), ""));
            }else {
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
                                    view.dismissMyDialog();
                                }
                            }
                        });
                    }
                });
            }
        }else if (type == Type_rename){
            if (scenes.size() == 0){
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
    public void switchQuerySceneGroupResult(List<SceneGroup> sceneGroups, int type) {
        if (type == Type_add) {
            if (sceneGroups.size() == 0) {
                SceneGroup sceneGroup = new SceneGroup(MyApplication.getOnlineUser().getEmail(), view.getInputTextMyDialog(), 0, "", DateUtil.getTime(), DateUtil.getTime());
                model.addSceneGroup(sceneGroup);
                ARouter.getInstance().build(Constant.ACTIVITY_URL_ManageScene).withString("sceneGroupName", view.getInputTextMyDialog()).navigation();
            } else {
                view.showMyDialog(MyDialog.Read_OneBtn_NormalTitle_BlueOneBtn, "创建场景群组", "该场景群组名称已存在，请尝试使用其它名称。", "重试", new MyDialog.NeutralOnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view.dismissMyDialog();
                        view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn, "创建场景群组", "", "取消", null, "创建", new MyDialog.PositiveOnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (view.getInputTextMyDialog().isEmpty()){
                                    SnackBarUtil.show(v, "请输入场景群组名称");
                                }else {
                                    model.querySceneGroup(view.getInputTextMyDialog(), Type_add);
                                    view.dismissMyDialog();
                                }
                            }
                        });
                    }
                });
            }
        }else if (type == Type_rename){
            if (sceneGroups.size() == 0) {
                sceneGroupList.get(scenePosition - sceneList.size() - 1).setName(view.getInputTextMyDialog());
                sceneGroupList.get(scenePosition - sceneList.size() - 1).setModifiedDate(DateUtil.getTime());
                model.updateSceneGroup(sceneGroupList.get(scenePosition - sceneList.size() - 1));
                view.dismissMyDialog();
            } else {
                view.dismissMyDialog();
                view.showMyDialog(MyDialog.Read_OneBtn_NormalTitle_BlueOneBtn, "重命名", "该场景群组名称已存在，请尝试使用其它名称。", "重试", new MyDialog.NeutralOnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view.dismissMyDialog();
                        view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn, "重命名", sceneGroupList.get(scenePosition - sceneList.size() - 1).getName(), "取消", null, "重命名", new MyDialog.PositiveOnClickListener() {
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
    public void sceneMenuSwitch(int position) {
        scenePosition = position;
        model.getThreePointMenu();
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
    public void receiveQuerySceneFromSceneGroup(List<Scene> scenes, int type) {
        if (type == Type_delete){
            for (Scene scene : scenes) {
                model.deleteScene(scene);
            }
        }else if (type == Type_update){
            for (Scene scene : scenes){
                scene.setSceneGroupName("");
                model.updateScene(scene);
            }
        }
    }

    @Override
    public void getDeviceListFromModel() {
        model.getDeviceList();
    }

    @Override
    public void receiveDeviceList(DeviceMessage deviceMessage) {
        switch (deviceMessage.getCode()){
            case 200:
                if (deviceMessage.getData().getContentVersion() > MyApplication.getInstance().getDeviceListVersion()){
                    MyApplication.getInstance().setDeviceListVersion(deviceMessage.getData().getContentVersion());
                    HashMap<String, Device> deviceHashMap = new HashMap<String, Device>();
                    for (Device device: deviceMessage.getData().getDeviceList()){
                        deviceHashMap.put(device.getDeviceId(), device);
                        if (MyApplication.getDeviceHashMap().isEmpty()){
                            model.addDevice(device);
                        }else {
                            model.updateDevice(device);
                        }
                    }
                    MyApplication.setDeviceHashMap(deviceHashMap);
                }
                break;
            case 1001:
            case 1002:
            case 1003:
            case 1004:
            case 1005:
            case 1006:
            case 1007:
            case 1008:
            case 1009:
            case 1010:
            case 1011:
                view.showMyDialog(MyDialog.Read_OneBtn_WarningTitle_BlueOneBtn,"错误", deviceMessage.getMsg().toString(),"重试", null);
                break;
        }
    }

    @Override
    public void getOnlineUserFromModel() {
        model.getOnlineUser();
    }

    @Override
    public void receiveOnlineUser(List<User> users) {
        if (!users.isEmpty()){
            MyApplication.setOnlineUser(users.get(0));
            view.updateRecycleView();
        }
    }

}
