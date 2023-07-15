package com.example.NanLinkDemo.mvp.presenter.Impl;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.FixtureGroup;
import com.example.NanLinkDemo.DB.bean.Scene;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.bean.DeviceDataMessage;
import com.example.NanLinkDemo.bean.Menu;
import com.example.NanLinkDemo.mvp.adapter.ThreePointAdapter;
import com.example.NanLinkDemo.mvp.model.Impl.SceneModelImpl;
import com.example.NanLinkDemo.mvp.presenter.ScenePresenter;
import com.example.NanLinkDemo.mvp.view.SceneView;
import com.example.NanLinkDemo.mvp.widget.ControlActivity;
import com.example.NanLinkDemo.ui.MyDialog;
import com.example.NanLinkDemo.util.Constant;
import com.example.NanLinkDemo.util.DateUtil;
import com.example.NanLinkDemo.util.SnackBarUtil;
import com.example.NanLinkDemo.util.SortUtil;
import com.example.NanLinkDemo.util.TransformUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class ScenePresenterImpl implements ScenePresenter {
    private final SceneView view;
    private final SceneModelImpl model;
    private ArrayList<Menu> menuArrayList;

    private ArrayList<Menu> sortArrayList;

    private ArrayList<Fixture> fixtureList;
    private ArrayList<FixtureGroup> fixtureGroupList;


    private ArrayList<Menu> settingArrayList;


    public ScenePresenterImpl(SceneView view) {
        this.view = view;
        model = new SceneModelImpl(this);
    }


    @Override
    public void switchOnclick(View v) {
        switch (v.getId()) {
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
                ARouter.getInstance().build(Constant.ACTIVITY_URL_AddNewFixture).navigation();
                break;
            case R.id.toolbar_right_btn:
                view.initMenu();
                break;
        }
    }

    @Override
    public void sortSwitch(int position) {
        switch (position) {
            case 0:
                view.initMenu();
                break;
            case 1:
                break;
            default:
                if (MyApplication.getScene().getSortPosition() != position - 2) {
                    sortArrayList.get(MyApplication.getScene().getSortPosition() + 2).setIconResId(R.drawable.ic_unselected);
                    MyApplication.getScene().setSortPosition(position - 2);
                    sortArrayList.get(position).setIconResId(R.drawable.ic_selected);
                    model.updateScene(MyApplication.getScene());
                    view.showMenu(sortArrayList);
                }
                view.setData(fixtureGroupList, fixtureList);
                break;
        }
    }

    @Override
    public void settingSwitch(int position) {
        switch (position) {
            case 0:
                view.initMenu();
                break;
            case 2:
                view.closeDrawLayout();
                view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn, settingArrayList.get(position).getText(), MyApplication.getScene().getName(), "取消", null, "重命名", new MyDialog.PositiveOnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (view.getInputTextMyDialog().isEmpty()) {
                            SnackBarUtil.show(v, "请输入场景名称");
                        } else {
                            model.queryScene(view.getInputTextMyDialog());
                        }
                    }
                });
                break;
            case 3:
                view.closeDrawLayout();
                view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn_Remark, settingArrayList.get(position).getText(), MyApplication.getScene().getRemark(), "取消", null, "完成", new MyDialog.PositiveOnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyApplication.getScene().setRemark(view.getInputTextMyDialog());
                        MyApplication.getScene().setModifiedDate(DateUtil.getTime());
                        model.updateScene(MyApplication.getScene());
                        view.dismissMyDialog();
                    }
                });
                break;
            case 4:
                view.closeDrawLayout();
                view.showMyDialog(MyDialog.Read_TwoBtn_WarningTitle_WarningTwoBtn, settingArrayList.get(position).getText(), "是否要删除该场景?", "取消", null, "删除", new MyDialog.PositiveOnClickListener() {
                    @Override
                    public void onClick(View v) {
                        model.deleteScene(MyApplication.getScene());
                        for (FixtureGroup fixtureGroup : fixtureGroupList) {
                            model.deleteFixtureGroup(fixtureGroup);
                        }
                        fixtureGroupList = new ArrayList<>();
                        for (Fixture fixture : fixtureList) {
                            model.deleteFixture(fixture);
                        }
                        fixtureList = new ArrayList<>();
                        view.dismissMyDialog();
                        view.finish();
                    }
                });
                break;

        }
    }

    @Override
    public void getFixtureListFromModel() {
        model.queryFixtureGroupList();
    }

    @Override
    public void getMenuFromModel() {
        model.getMenu();
    }

    @Override
    public void menuSwitch(int position) {
        switch (position) {
            case 1:
                view.closeDrawLayout();
                ARouter.getInstance().build(Constant.ACTIVITY_URL_AddNewFixture).navigation();
                break;
            case 2:
                view.closeDrawLayout();
                view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn, menuArrayList.get(position).getText(), "", "取消", null, "创建", new MyDialog.PositiveOnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAddFixtureGroup(v);
                    }
                });
                break;
            case 3:
                model.getSortList();
                break;
            case 5:
                view.closeDrawLayout();
                ARouter.getInstance().build(Constant.ACTIVITY_URL_Controller).navigation();
                break;
            case 7:
                view.closeDrawLayout();
                ARouter.getInstance().build(Constant.ACTIVITY_URL_ControlMode).navigation();
                break;
            case 8:
            case 10:
                view.closeDrawLayout();
                break;
            case 12:
                model.getSettingList();
                break;
        }
    }

    private void checkAddFixtureGroup(View v) {
        if (view.getInputTextMyDialog().isEmpty()) {
            SnackBarUtil.show(v, "请输入设备群组名称");
        } else {
            for (FixtureGroup fixtureGroup : fixtureGroupList) {
                if (fixtureGroup.getName().equals(view.getInputTextMyDialog())) {
                    view.dismissMyDialog();
                    view.showMyDialog(MyDialog.Read_OneBtn_NormalTitle_BlueOneBtn, "创建设备群组", "该设备群组名称已存在，\n请尝试使用其它名称。", "重试", new MyDialog.NeutralOnClickListener() {
                        @Override
                        public void onClick(View v) {
                            view.dismissMyDialog();
                            view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn, "创建设备群组", "", "取消", null, "创建", new MyDialog.PositiveOnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    checkAddFixtureGroup(v);
                                }
                            });
                        }
                    });
                    return;
                }
            }
            FixtureGroup fixtureGroup = new FixtureGroup(MyApplication.getOnlineUser().getEmail(), MyApplication.getScene().getName(), view.getInputTextMyDialog(), 0);
            model.addFixtureGroup(fixtureGroup);
            MyApplication.getFixtureGroups().add(fixtureGroup);
            MyApplication.getScene().setModifiedDate(DateUtil.getTime());
            model.updateScene(MyApplication.getScene());
            view.dismissMyDialog();
            ARouter.getInstance().build(Constant.ACTIVITY_URL_ManageFixture).withString("fixtureGroupName", view.getInputTextMyDialog()).navigation();
        }
    }

    private void checkRenameFixtureGroup(View v, int fixtureGroupPosition) {
        if (view.getInputTextMyDialog().isEmpty()) {
            SnackBarUtil.show(v, "请输入设备群组名称");
        } else {
            for (FixtureGroup fixtureGroup : fixtureGroupList) {
                if (fixtureGroup.getName().equals(view.getInputTextMyDialog())) {
                    view.dismissMyDialog();
                    view.showMyDialog(MyDialog.Read_OneBtn_NormalTitle_BlueOneBtn, "重命名", "该设备群组名称已存在，\n请尝试使用其它名称。", "重试", new MyDialog.NeutralOnClickListener() {
                        @Override
                        public void onClick(View v) {
                            view.dismissMyDialog();
                            view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn, "重命名", "", "取消", null, "重命名", new MyDialog.PositiveOnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    checkRenameFixtureGroup(v, fixtureGroupPosition);
                                }
                            });
                        }
                    });
                    return;
                }
            }
            for (Fixture fixture : fixtureList){
                if (fixture.getFixtureGroupName().equals(fixtureGroupList.get(fixtureGroupPosition).getName())) {
                    fixture.setFixtureGroupName(view.getInputTextMyDialog());
                    model.updateFixture(fixture);
                }
            }
            fixtureGroupList.get(fixtureGroupPosition).setName(view.getInputTextMyDialog());
            model.updateFixtureGroup(fixtureGroupList.get(fixtureGroupPosition));

            MyApplication.getFixtureGroups().get(fixtureGroupPosition).setName(view.getInputTextMyDialog());
            MyApplication.getScene().setModifiedDate(DateUtil.getTime());
            model.updateScene(MyApplication.getScene());
            view.dismissMyDialog();
        }
    }

    @Override
    public void FixtureListSwitch(int position) {
        if (fixtureGroupList.isEmpty()) {
            if (position > 0) {
                ARouter.getInstance().build(Constant.ACTIVITY_URL_Control).withInt("id", fixtureList.get(position - 1).getId()).withInt("type", ControlActivity.TYPE_FIXTURE).navigation();
            }
        } else {
            if (position > 0 && position <= fixtureGroupList.size()) {
                ARouter.getInstance().build(Constant.ACTIVITY_URL_Control).withInt("id", fixtureGroupList.get(position - 1).getId()).withInt("type", ControlActivity.TYPE_FIXTURE_GROUP).navigation();
            } else if (position > fixtureGroupList.size() + 1) {
                ARouter.getInstance().build(Constant.ACTIVITY_URL_Control).withInt("id", fixtureList.get(position - fixtureGroupList.size() - 2).getId()).withInt("type", ControlActivity.TYPE_FIXTURE).navigation();
            }
        }
    }

    @Override
    public void FixtureMenuSwitch(int position) {
        if (fixtureGroupList.size() > 0) {
            if (position < fixtureGroupList.size() + 1) {
                model.getFixtureGroupMenu(position - 1);
            } else {
                model.getFixtureMenu(position - fixtureGroupList.size() - 2);
            }
        } else {
            model.getFixtureMenu(position - 1);
        }
    }

    @Override
    public void FixtureMenuSwitch(Fixture fixture) {
            model.getFixtureMenu(fixture);
    }

    @Override
    public void FixtureListDim(int position) {
        if (fixtureGroupList.size() > 0) {
            if (position < fixtureGroupList.size() + 1) {
                FixtureGroup fixtureGroup = fixtureGroupList.get(position - 1);
                fixtureGroup.setData(updateDim(fixtureGroup.getData()));
                model.updateFixtureGroup(fixtureGroup);
            } else {
                Fixture fixture = fixtureList.get(position - fixtureGroupList.size() - 2);
                fixture.setData(updateDim(fixture.getData()));
                model.updateFixture(fixture);
            }
        } else {
            Fixture fixture = fixtureList.get(position - 1);
            fixture.setData(updateDim(fixture.getData()));
            model.updateFixture(fixture);
        }
        view.setData(fixtureGroupList, fixtureList);

    }

    private String updateDim(String data) {
        if (!data.isEmpty()) {
            Gson gson = new Gson();
            DeviceDataMessage.Data deviceData = gson.fromJson(data, DeviceDataMessage.Data.class);
            deviceData.setDimItem(String.valueOf(-Integer.parseInt(deviceData.getDimItem())));
            gson = new GsonBuilder().setPrettyPrinting().create();
            data = gson.toJson(deviceData);
            return data;
        }
        return "";
    }

    @Override
    public void receiveMenu(ArrayList<Menu> menuArrayList) {
        this.menuArrayList = menuArrayList;
        view.showMenu(menuArrayList);
        view.openDrawLayout();
    }

    @Override
    public void showSortListToView(ArrayList<Menu> sortArrayList) {
        sortArrayList.get(MyApplication.getScene().getSortPosition() + 2).setIconResId(R.drawable.ic_selected);
        this.sortArrayList = sortArrayList;
        view.showSortList(sortArrayList);
    }


    @Override
    public void showSettingListToView(ArrayList<Menu> settingArrayList) {
        this.settingArrayList = settingArrayList;
        view.showSettingList(settingArrayList);
    }


    @Override
    public void updateFixtureList() {
        getFixtureListFromModel();
    }


    @Override
    public void receiveFixtureGroupMenu
            (ArrayList<String> fixtureGroupMenuList, int fixtureGroupPosition) {
        fixtureGroupMenuList.set(0, fixtureGroupList.get(fixtureGroupPosition).getName());
        view.showSettingDialog(fixtureGroupMenuList, new ThreePointAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                switch (position) {
                    case 1:
                        view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn, fixtureGroupMenuList.get(position), fixtureGroupList.get(fixtureGroupPosition).getName(), "取消", null, "重命名", new MyDialog.PositiveOnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checkRenameFixtureGroup(v, fixtureGroupPosition);
                            }
                        });
                        view.dismissSettingDialog();
                        break;
                    case 2:
                        ARouter.getInstance().build(Constant.ACTIVITY_URL_ManageFixture).withString(
                                "fixtureGroupName", fixtureGroupList.get(fixtureGroupPosition).getName()).navigation();
                        view.dismissSettingDialog();
                        break;
                    case 3:
                        view.showMyDialog(MyDialog.Read_TwoBtn_WarningTitle_WarningTwoBtn, fixtureGroupMenuList.get(position), "是否确定要删除该设备群组?", "取消", null, "删除", new MyDialog.PositiveOnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        });
                        view.dismissSettingDialog();
                        break;
                    case 4:
                        view.dismissSettingDialog();
                }
            }
        });
    }

    @Override
    public void receiveFixtureMenu(ArrayList<String> fixtureMenuList,
                                   int fixturePosition) {
        fixtureMenuList.set(0, fixtureList.get(fixturePosition).getName());
        view.showSettingDialog(fixtureMenuList, new ThreePointAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                switch (position) {
                    case 1:
                        view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn, fixtureMenuList.get(position), fixtureList.get(fixturePosition).getName(), "取消", null, "重命名", new MyDialog.PositiveOnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (view.getInputTextMyDialog().isEmpty()) {
                                    SnackBarUtil.show(v, "请输入设备名称");
                                } else {
                                    Fixture fixture = fixtureList.get(fixturePosition);
                                    fixture.setName(view.getInputTextMyDialog());
                                    fixtureList.set(fixturePosition, fixture);
                                    model.updateFixture(fixture);
                                    MyApplication.setFixtures(fixtureList);
                                    view.dismissMyDialog();
                                }
                            }
                        });
                        view.dismissSettingDialog();
                        break;
                    case 2:
                        view.showMyDialog(MyDialog.Read_TwoBtn_NormalTitle_BlueTwoBtn, fixtureMenuList.get(position), "是否确定要重置该设备名称?", "该设备原始名称:\n" + fixtureList.get(fixturePosition).getFixTureName(), "取消", null, "确定", new MyDialog.PositiveOnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Fixture fixture = fixtureList.get(fixturePosition);
                                fixture.setName(fixture.getFixTureName());
                                fixtureList.set(fixturePosition, fixture);
                                model.updateFixture(fixture);
                                MyApplication.setFixtures(fixtureList);
                                view.dismissMyDialog();
                            }
                        });
                        view.dismissSettingDialog();
                        break;
                    case 3:
                        changeCH(position, fixtureMenuList, fixturePosition);

                        break;
                    case 4:
                        view.showMyDialog(MyDialog.Read_TwoBtn_WarningTitle_WarningTwoBtn, fixtureMenuList.get(position), "是否确定要删除该设备?", fixtureList.get(fixturePosition).getName() + "\nCH: " + TransformUtil.updateCH(fixtureList.get(fixturePosition).getCH()), "取消", null, "删除", new MyDialog.PositiveOnClickListener() {
                            @Override
                            public void onClick(View v) {
                                model.deleteFixture(fixtureList.get(fixturePosition));
                                fixtureList.remove(fixtureList.get(fixturePosition));
                                MyApplication.setFixtures(fixtureList);
                                MyApplication.getScene().setFixtureNum(fixtureList.size());
                                model.updateScene(MyApplication.getScene());
                                view.dismissMyDialog();
                            }
                        });
                        view.dismissSettingDialog();
                        break;
                    case 5:
                        view.dismissSettingDialog();
                }
            }
        });
    }

    @Override
    public void receiveFixtureMenu(ArrayList<String> fixtureMenuList,
                                   Fixture fixture) {
        fixtureMenuList.set(0, fixture.getName());
        view.showSettingDialog(fixtureMenuList, new ThreePointAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                switch (position) {
                    case 1:
                        view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn, fixtureMenuList.get(position), fixture.getName(), "取消", null, "重命名", new MyDialog.PositiveOnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (view.getInputTextMyDialog().isEmpty()) {
                                    SnackBarUtil.show(v, "请输入设备名称");
                                } else {
                                    for (int i = 0; i < fixtureList.size(); i++){
                                        if (fixture.getId() == fixtureList.get(i).getId()){
                                            fixture.setName(view.getInputTextMyDialog());
                                            fixtureList.set(i, fixture);
                                        }
                                    }
                                    model.updateFixture(fixture);
                                    MyApplication.setFixtures(fixtureList);
                                    view.dismissMyDialog();
                                }
                            }
                        });
                        view.dismissSettingDialog();
                        break;
                    case 2:
                        view.showMyDialog(MyDialog.Read_TwoBtn_NormalTitle_BlueTwoBtn, fixtureMenuList.get(position), "是否确定要重置该设备名称?", "该设备原始名称:\n" + fixture.getFixTureName(), "取消", null, "确定", new MyDialog.PositiveOnClickListener() {
                            @Override
                            public void onClick(View v) {
                                for (int i = 0; i < fixtureList.size(); i++){
                                    if (fixture.getId() == fixtureList.get(i).getId()){
                                        fixture.setName(fixture.getFixTureName());
                                        fixtureList.set(i, fixture);
                                    }
                                }
                                model.updateFixture(fixture);
                                MyApplication.setFixtures(fixtureList);
                                view.dismissMyDialog();
                            }
                        });
                        view.dismissSettingDialog();
                        break;
                    case 3:
                        changeCH(position, fixtureMenuList, fixture);

                        break;
                    case 4:
                        view.showMyDialog(MyDialog.Read_TwoBtn_WarningTitle_WarningTwoBtn, fixtureMenuList.get(position), "是否确定要删除该设备?", fixture.getName() + "\nCH: " + TransformUtil.updateCH(fixture.getCH()), "取消", null, "删除", new MyDialog.PositiveOnClickListener() {
                            @Override
                            public void onClick(View v) {
                                for (FixtureGroup fixtureGroup : fixtureGroupList){
                                    if (fixture.getFixtureGroupName().equals(fixtureGroup.getName())){
                                        fixtureGroup.setFixtureNum(fixtureGroup.getFixtureNum() - 1);
                                        model.updateFixtureGroup(fixtureGroup);
                                    }
                                }
                                model.deleteFixture(fixture);
                                fixtureList.remove(fixture);
                                MyApplication.setFixtures(fixtureList);
                                MyApplication.getScene().setFixtureNum(fixtureList.size());
                                model.updateScene(MyApplication.getScene());
                                view.dismissMyDialog();
                            }
                        });
                        view.dismissSettingDialog();
                        break;
                    case 5:
                        view.dismissSettingDialog();
                }
            }
        });
    }

    private void changeCH(int position, ArrayList<String> fixtureMenuList, Fixture fixture) {
        view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn_CH, fixtureMenuList.get(position), TransformUtil.updateCH(fixture.getCH()), "请输入一个介于1和512之间的数值", "取消", null, "确定", new MyDialog.PositiveOnClickListener() {
            @Override
            public void onClick(View v) {
                for (Fixture fixture : fixtureList) {
                    if (fixture.getCH() == (Integer.parseInt(view.getInputTextMyDialog()))) {
                        view.dismissMyDialog();
                        view.showMyDialog(MyDialog.Read_TwoBtn_NormalTitle_WhiteTwoBtn, fixtureMenuList.get(position), "列表中有另一台设备正在使用该\n地址码，请尝试其它地址码。", "取消", null, "重试", new MyDialog.PositiveOnClickListener() {
                            @Override
                            public void onClick(View v) {
                                view.dismissMyDialog();
                                changeCH(position, fixtureMenuList, fixture);
                            }
                        });
                        return;
                    }

                }
                for (int i = 0; i < fixtureList.size(); i++){
                    if (fixture.getId() == fixtureList.get(i).getId()){
                        fixture.setCH(Integer.parseInt(view.getInputTextMyDialog()));
                        fixtureList.set(i, fixture);
                    }
                }
                model.updateFixture(fixture);
                MyApplication.setFixtures(fixtureList);
                view.dismissMyDialog();
            }
        });
        view.dismissSettingDialog();
    }

    private void changeCH(int position, ArrayList<String> fixtureMenuList, int fixturePosition) {
        view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn_CH, fixtureMenuList.get(position), TransformUtil.updateCH(fixtureList.get(fixturePosition).getCH()), "请输入一个介于1和512之间的数值", "取消", null, "确定", new MyDialog.PositiveOnClickListener() {
            @Override
            public void onClick(View v) {
                for (Fixture fixture : fixtureList) {
                    if (fixture.getCH() == (Integer.parseInt(view.getInputTextMyDialog()))) {
                        view.dismissMyDialog();
                        view.showMyDialog(MyDialog.Read_TwoBtn_NormalTitle_WhiteTwoBtn, fixtureMenuList.get(position), "列表中有另一台设备正在使用该\n地址码，请尝试其它地址码。", "取消", null, "重试", new MyDialog.PositiveOnClickListener() {
                            @Override
                            public void onClick(View v) {
                                view.dismissMyDialog();
                                changeCH(position, fixtureMenuList, fixturePosition);
                            }
                        });
                        return;
                    }

                }
                Fixture fixture = fixtureList.get(fixturePosition);
                fixture.setCH(Integer.parseInt(view.getInputTextMyDialog()));
                fixtureList.set(fixturePosition, fixture);
                model.updateFixture(fixture);
                MyApplication.setFixtures(fixtureList);
                view.dismissMyDialog();
            }
        });
        view.dismissSettingDialog();
    }

    @Override
    public void receiveFixtureGroupList
            (List<FixtureGroup> fixtureGroups) {
        fixtureGroupList = (ArrayList<FixtureGroup>) fixtureGroups;
        MyApplication.setFixtureGroups(fixtureGroupList);
        model.queryFixtureList();
    }

    @Override
    public void receiveFixtureList(List<Fixture> fixtures) {
        fixtureList = (ArrayList<Fixture>) fixtures;
        MyApplication.setFixtures(fixtureList);
        if (checkHasData()) {
            view.setData(fixtureGroupList, fixtureList);
        }
        view.updateAddNewFixtureLogo(!checkHasData());
    }

    @Override
    public void receiveSceneGroup(List<Scene> scenes) {
        if (scenes.isEmpty()) {
            MyApplication.getScene().setName(view.getInputTextMyDialog());
            MyApplication.getScene().setModifiedDate(DateUtil.getTime());
            model.updateScene(MyApplication.getScene());
            view.setTitle(MyApplication.getScene().getName());
            for (FixtureGroup fixtureGroup : fixtureGroupList) {
                fixtureGroup.setSceneName(MyApplication.getScene().getName());
                model.updateFixtureGroup(fixtureGroup);
            }
            for (Fixture fixture : fixtureList) {
                fixture.setSceneName(MyApplication.getScene().getName());
                model.updateFixture(fixture);
            }
            view.dismissMyDialog();
        } else {
            view.dismissMyDialog();
            view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn, "重命名", MyApplication.getScene().getName(), "取消", null, "重命名", new MyDialog.PositiveOnClickListener() {
                @Override
                public void onClick(View v) {
                    if (view.getInputTextMyDialog().isEmpty()) {
                        SnackBarUtil.show(v, "请输入场景名称");
                    } else {
                        model.queryScene(view.getInputTextMyDialog());
                    }
                }
            });
        }
    }


    public boolean checkHasData() {
        return fixtureList == null || !fixtureList.isEmpty() || fixtureGroupList == null || !fixtureGroupList.isEmpty();
    }

}
