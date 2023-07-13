package com.example.NanLinkDemo.mvp.presenter.Impl;

import android.content.Intent;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.Scene;
import com.example.NanLinkDemo.bean.Add24GFixture;
import com.example.NanLinkDemo.bean.Device;
import com.example.NanLinkDemo.mvp.adapter.Add24GAdapter;
import com.example.NanLinkDemo.mvp.model.Impl.Add24GFixtureModelImpl;
import com.example.NanLinkDemo.mvp.presenter.Add24GFixturePresenter;
import com.example.NanLinkDemo.mvp.view.Add24GFixtureView;
import com.example.NanLinkDemo.mvp.widget.Add24GFixtureActivity;
import com.example.NanLinkDemo.ui.MyDialog;
import com.example.NanLinkDemo.util.Constant;
import com.example.NanLinkDemo.util.DataUtil;
import com.example.NanLinkDemo.util.DateUtil;
import com.example.NanLinkDemo.util.SnackBarUtil;
import com.example.NanLinkDemo.util.TransformUtil;

import java.util.ArrayList;

public class Add24GFixturePresenterImpl implements Add24GFixturePresenter {
    private final Add24GFixtureView view;
    private final Add24GFixtureModelImpl model;
    private ArrayList<Add24GFixture> add24GFixtures;
    private ArrayList<Add24GFixture> unPassCHList, passCHList;

    public Add24GFixturePresenterImpl(Add24GFixtureView view) {
        this.view = view;
        this.model = new Add24GFixtureModelImpl(this);
    }

    @Override
    public void getDataFromView() {
        model.getBoxViewData();
        model.getListData();
    }

    @Override
    public void setDataToView(ArrayList<Add24GFixture> add24GFixtures) {
        this.add24GFixtures = add24GFixtures;
        view.setData(add24GFixtures);
    }

    @Override
    public void setDataToBoxView(Integer boxViewId, String title, ArrayList<String> dataList, int checkIndex) {
        view.updateBoxView(boxViewId, title, dataList, checkIndex);
    }



    @Override
    public void onClick() {
        checkFixtureList(view.getFixtureArrayList());

    }

    // 检查添加是设备是否重复的CH
    private void checkFixtureList(ArrayList<Add24GFixture> fixtureArrayList) {
        sortSelectedDeviceList(fixtureArrayList);
        notice();
    }

    private void notice() {
        if (unPassCHList.isEmpty()) {
            String deviceId = "";
            switch (view.getCheckIndexType()){
                case 0:
                    deviceId = "FFFFF1";
                    break;
                case 1:
                    switch (view.getCheckIndexCCTRange()){
                        case 0:
                            deviceId = "FFFFF2";
                            break;
                        case 1:
                            deviceId = "FFFFF3";
                            break;
                        case 2:
                            deviceId = "FFFFF4";
                            break;
                        case 3:
                            deviceId = "FFFFF5";
                            break;
                    }
                case 2:
                    switch (view.getCheckIndexCCTRange()){
                        case 0:
                            switch (view.getCheckIndexGM()){
                                case 0:
                                    deviceId = "FFFFF6";
                                    break;
                                case 1:
                                    deviceId = "FFFFFA";
                                    break;
                            }
                            break;
                        case 1:
                            switch (view.getCheckIndexGM()){
                                case 0:
                                    deviceId = "FFFFF7";
                                    break;
                                case 1:
                                    deviceId = "FFFFFB";
                                    break;
                            }
                            break;
                        case 2:
                            switch (view.getCheckIndexGM()){
                                case 0:
                                    deviceId = "FFFFF8";
                                    break;
                                case 1:
                                    deviceId = "FFFFFC";
                                    break;
                            }
                            break;
                        case 3:
                            switch (view.getCheckIndexGM()){
                                case 0:
                                    deviceId = "FFFFF9";
                                    break;
                                case 1:
                                    deviceId = "FFFFFD";
                                    break;
                            }
                            break;
                    }

            }
            String finalDeviceId = deviceId;
            DataUtil.getDeviceData((Add24GFixtureActivity)view, deviceId, 1, new DataUtil.onReceiveDeviceDataListener() {
                @Override
                public void ReceiveDeviceData(String data) {
                    for (Add24GFixture add24GFixture : add24GFixtures){
                        Fixture fixture = new Fixture(MyApplication.getOnlineUser().getEmail(), MyApplication.getScene().getName(), add24GFixture.getName(), add24GFixture.getCH(), finalDeviceId, "2.4G", "");
                        fixture.setData(data);
                        MyApplication.getFixtures().add(fixture);
                        model.addFixture(fixture);
                    }
                    Scene scene = MyApplication.getScene();
                    scene.setFixtureNum(MyApplication.getFixtures().size());
                    scene.setModifiedDate(DateUtil.getTime());
                    MyApplication.setScene(scene);
                    model.updateScene(scene);
                    ARouter.getInstance().build(Constant.ACTIVITY_URL_Scene).withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).navigation();

                }
            });

        } else {
            view.showMyDialog(MyDialog.Read_TwoBtn_NormalTitle_WhiteTwoBtn, "更改地址码", "有" + unPassCHList.size() + "台灯光设备的地址码\n因与其它设备重复需要更改", "取消", null, "更改", new MyDialog.PositiveOnClickListener() {
                @Override
                public void onClick(View v) {
                    view.dismissMyDialog();
                    updateCH();
                }
            });
        }
    }

    private void updateCH() {
        if (unPassCHList.isEmpty()) {
            notice();
        } else {
            Add24GFixture device = unPassCHList.get(0);
            view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn_AddFixture_CH, "更改地址码", device.getName(), TransformUtil.updateCH(device.getCH()), "取消", null, "确定", new MyDialog.PositiveOnClickListener() {
                @Override
                public void onClick(View v) {
                    if (view.getInputTextMyDialog().isEmpty()) {
                        SnackBarUtil.show(v, "请输入介于1-512之间的数值");
                    } else {
                        if (isPassCH(Integer.parseInt(view.getInputTextMyDialog()))) {
                            view.dismissMyDialog();
                            device.setCH(Integer.parseInt(view.getInputTextMyDialog()));
                            unPassCHList.remove(device);
                            passCHList.add(device);
                            updateCH();
                        } else {
                            view.dismissMyDialog();
                            view.showMyDialog(MyDialog.Read_TwoBtn_NormalTitle_WhiteTwoBtn, "更改地址码", "列表中有另一台设备正在使用该\n地址码, 请尝试其它地址码。", "取消", null, "重试", new MyDialog.PositiveOnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    view.dismissMyDialog();
                                    updateCH();
                                }
                            });
                        }
                    }
                }
            });
        }
    }


    private void sortSelectedDeviceList(ArrayList<Add24GFixture> fixtureArrayList) {
        unPassCHList = new ArrayList<>();
        passCHList = new ArrayList<>();

        for (Add24GFixture device : fixtureArrayList) {
                if (isPassCH(device.getCH())) {
                    passCHList.add(device);
                } else {
                    unPassCHList.add(device);
                }
        }
    }

    private boolean isPassCH(int CH) {
        for (int i = 0; i < MyApplication.getFixtures().size(); i++) {
            if (CH == MyApplication.getFixtures().get(i).getCH()) {
                return false;
            }
        }
        for (int i = 0; i < passCHList.size(); i++) {
            if (CH == passCHList.get(i).getCH()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void setListener() {
        view.setListViewOnOutRangeListener(new Add24GAdapter.OnOutRangeListener() {
            @Override
            public void onOutRange() {
                view.showMyDialog(MyDialog.Read_OneBtn_NoTitle_BlueOneBtn, "", "请输入介于 1 - 512\n之间的数值", "确定", null);
            }
        });
        view.setListViewOnCheckCompleteListener(new Add24GAdapter.OnCheckCompleteListener() {
            @Override
            public void CheckComplete(boolean complete) {
                view.setFinishBtn(complete);
            }
        });
    }


}
