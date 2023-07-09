package com.example.NanLinkDemo.mvp.presenter.Impl;


import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.view.View;


import androidx.collection.ArraySet;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.bean.Device;
import com.example.NanLinkDemo.mvp.model.Impl.ScanBleModelImpl;
import com.example.NanLinkDemo.mvp.presenter.ScanBlePresenter;
import com.example.NanLinkDemo.mvp.view.ScanBleView;
import com.example.NanLinkDemo.ui.MyDialog;
import com.example.NanLinkDemo.util.Constant;
import com.example.NanLinkDemo.util.DateUtil;
import com.example.NanLinkDemo.util.SnackBarUtil;
import com.example.NanLinkDemo.util.TransformUtil;

import java.time.temporal.TemporalUnit;
import java.util.ArrayList;


public class ScanBlePresenterImpl implements ScanBlePresenter {
    private final ScanBleView view;
    private final ScanBleModelImpl model;

    private boolean allSelected = false;

    ArrayList<Device> deviceList = new ArrayList<>();
    private ArrayList<byte[]> uuidList = new ArrayList<>();
    private ArrayList<Device> selectedDeviceList;
    private ArrayList<Device> unsetCHList, unPassCHList, passCHList;

    public ScanBlePresenterImpl(ScanBleView view) {
        this.view = view;
        this.model = new ScanBleModelImpl(this);
    }

    @Override
    public void onClickSwitch(int position) {
        deviceList.get(position).setSelected(!deviceList.get(position).isSelected());
        view.showBle(deviceList);
    }

    @Override
    public void getListDataFromView() {
        model.getListData();
    }

    @Override
    public void onClickSwitch(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left_btn:
                view.finish();
                break;
            case R.id.all_selected:
                allSelected = !allSelected;
                for (Device device : deviceList) {
                    device.setSelected(!allSelected);
                }
                view.showBle(deviceList);
                break;
            case R.id.complete:
                selectedDeviceList = new ArrayList<>();
                for (Device device : deviceList) {
                    if (device.isSelected()) {
                        selectedDeviceList.add(device);
                    }
                }
                checkDeviceList(selectedDeviceList);
                break;
            case R.id.toolbar_right_btn:
                deviceList.clear();
                view.showBle(deviceList);
                view.startScan();
        }
    }

    private void checkDeviceList(ArrayList<Device> selectedDeviceList) {
        sortSelectedDeviceList(selectedDeviceList);
        notice();
    }

    private void notice() {
        if (unsetCHList.isEmpty() && unPassCHList.isEmpty()) {
            view.startLoading();
            for (Device device : passCHList) {
                model.addBleFixture(device);
            }
            view.stopLoading();
            ARouter.getInstance().build(Constant.ACTIVITY_URL_Scene).withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).navigation();
            view.finish();
        } else {
            if (!unsetCHList.isEmpty()) {
                view.showMyDialog(MyDialog.Read_TwoBtn_NormalTitle_WhiteTwoBtn, "设置地址码", "有" + unsetCHList.size() + "台灯光设备的地址码\n需要进行设置", "取消", null, "设置", new MyDialog.PositiveOnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view.dismissMyDialog();
                        setCH();
                    }
                });
                return;
            }
            view.showMyDialog(MyDialog.Read_TwoBtn_NormalTitle_WhiteTwoBtn, "更改地址码", "有" + unPassCHList.size() + "台灯光设备的地址码\n因与其它设备重复需要更改", "取消", null, "更改", new MyDialog.PositiveOnClickListener() {
                @Override
                public void onClick(View v) {
                    view.dismissMyDialog();
                    updateCH();
                }
            });
        }
    }

    private void setCH() {
        if (unsetCHList.isEmpty()) {
            notice();
        } else {
            Device device = unsetCHList.get(0);
            view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn_AddFixture_CH, "设置地址码", device.getNAME(), TransformUtil.updateCH(device.getCH()), "取消", null, "确定", new MyDialog.PositiveOnClickListener() {
                @Override
                public void onClick(View v) {
                    if (view.getInputTextMyDialog().isEmpty()) {
                        SnackBarUtil.show(v, "请输入介于1-512之间的数值");
                    } else {
                        if (isPassCH(Integer.parseInt(view.getInputTextMyDialog()))) {
                            view.dismissMyDialog();
                            device.setCH(Integer.parseInt(view.getInputTextMyDialog()));
                            unsetCHList.remove(device);
                            passCHList.add(device);
                            setCH();
                        } else {
                            view.dismissMyDialog();
                            view.showMyDialog(MyDialog.Read_TwoBtn_NormalTitle_WhiteTwoBtn, "设置地址码", "列表中有另一台设备正在使用该\n地址码, 请尝试其它地址码。", "取消", null, "重试", new MyDialog.PositiveOnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    view.dismissMyDialog();
                                    setCH();
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    private void updateCH() {
        if (unPassCHList.isEmpty()) {
            notice();
        } else {
            Device device = unPassCHList.get(0);
            view.showMyDialog(MyDialog.Write_TwoBtn_NormalTitle_BlueTwoBtn_AddFixture_CH, "更改地址码", device.getNAME(), TransformUtil.updateCH(device.getCH()), "取消", null, "确定", new MyDialog.PositiveOnClickListener() {
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

    private void sortSelectedDeviceList(ArrayList<Device> selectedDeviceList) {
        unPassCHList = new ArrayList<>();
        passCHList = new ArrayList<>();
        unsetCHList = new ArrayList<>();

        for (Device device : selectedDeviceList) {
            if (device.getCH() == 0) {
                unsetCHList.add(device);
            } else {
                if (isPassCH(device.getCH())) {
                    passCHList.add(device);
                } else {
                    unPassCHList.add(device);
                }
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
    public void handleResult(ScanResult result) {
        Device device = FeasyFilter(result) == null ? UserFilter(result) == null ? null : UserFilter(result) : FeasyFilter(result);
        if (device == null) {
            return;
        }
        if (deviceList.isEmpty()) {
            deviceList.add(device);
        } else {
            for (int i = 0; i < deviceList.size(); i++) {
                if (device.getUUID().equals(deviceList.get(i).getUUID())) {
                    boolean selected = deviceList.get(i).isSelected();
                    device.setSelected(selected);
                    deviceList.set(i, device);
                    break;
                }
                if (i == deviceList.size() - 1) {
                    deviceList.add(device);
                }
            }
        }
        view.showBle(deviceList);
    }

    @Override
    public void updateScene() {
        MyApplication.getScene().setFixtureNum(MyApplication.getScene().getFixtureNum() + 1);
        MyApplication.getScene().setModifiedDate(DateUtil.getTime());
        model.updateScene(MyApplication.getScene());
    }

    private Device FeasyFilter(ScanResult result) {
        ScanRecord scanRecord = result.getScanRecord();
        if (scanRecord != null && scanRecord.getDeviceName() != null && scanRecord.getDeviceName().startsWith("``NL")) {
            return new Device(result.getDevice().getAddress(), result.getScanRecord().getDeviceName());
        }
        return null;
    }

    private Device UserFilter(ScanResult result) {
        ScanRecord scanRecord = result.getScanRecord();
        if (scanRecord != null && scanRecord.getServiceData() != null && scanRecord.getServiceUuids() != null) {
            byte[] uuid = scanRecord.getServiceData().get(scanRecord.getServiceUuids().get(0));
            if ((uuid != null ? uuid.length : 0) > 14) {
                if (uuid[14] != 78) {
                    return null;
                }
                byte checkNum = 0;
                for (int i = 6; i < 15; i++) {
                    checkNum = (byte) (checkNum + uuid[i]);
                }
                if (checkNum != uuid[15]) {
                    return null;
                }
                if (String.format("%08X", uuid[13]).charAt(String.format("%08X", uuid[13]).length() - 1) == '1') {
                    return null;
                }
                return new Device(result.getDevice().getAddress(), uuid);
            }
        }
        return null;
    }


}
