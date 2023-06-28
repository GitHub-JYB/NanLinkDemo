package com.example.NanLinkDemo.mvp.presenter.Impl;


import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.view.View;


import com.alibaba.android.arouter.launcher.ARouter;
import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.bean.FeasyDevice;
import com.example.NanLinkDemo.mvp.model.Impl.ScanBleModelImpl;
import com.example.NanLinkDemo.mvp.presenter.ScanBlePresenter;
import com.example.NanLinkDemo.mvp.view.ScanBleView;
import com.example.NanLinkDemo.util.Constant;

import java.util.ArrayList;


public class ScanBlePresenterImpl implements ScanBlePresenter {
    private final ScanBleView view;
    private final ScanBleModelImpl model;

    private boolean allSelected = false;

    ArrayList<FeasyDevice> deviceList = new ArrayList<>();
    private ArrayList<byte[]> uuidList = new ArrayList<>();

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
                for (FeasyDevice device : deviceList) {
                    device.setSelected(!allSelected);
                }
                view.showBle(deviceList);
                break;
            case R.id.complete:
                view.startLoading();
                for (FeasyDevice device : deviceList) {
                    if (device.isSelected()) {
                        model.addBleFixture(device);
                    }
                }
                view.stopLoading();
                ARouter.getInstance().build(Constant.ACTIVITY_URL_Scene).withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).navigation();
                view.finish();
                break;
            case R.id.toolbar_right_btn:
                view.StartScan();
        }
    }

    @Override
    public void handleResult(ScanResult result) {
        if (result.getScanRecord().getServiceData() != null & result.getScanRecord().getServiceUuids() != null) {
            byte[] uuid = result.getScanRecord().getServiceData().get(result.getScanRecord().getServiceUuids().get(0));
            if ((uuid != null ? uuid.length : 0) > 14) {
                if (uuid[14] != 78) {
                    return;
                }
                byte checkNum = 0;
                for (int i = 6; i < 15; i++) {
                    checkNum = (byte) (checkNum + uuid[i]);
                }
                if (checkNum != uuid[15]) {
                    return;
                }
                if (String.format("%08X", uuid[13]).charAt(String.format("%08X", uuid[13]).length() - 1) == '1') {
                    return;
                }
                if (!deviceList.isEmpty()) {
                    for (int i = 0; i < deviceList.size(); i++) {
                        if (result.getDevice().getAddress().equals(deviceList.get(i).getUUID())) {
                            boolean selected = deviceList.get(i).isSelected();
                            deviceList.set(i, new FeasyDevice(result.getDevice().getAddress(), result.getScanRecord().getServiceData(result.getScanRecord().getServiceUuids().get(0))));
                            deviceList.get(i).setSelected(selected);
                            view.showBle(deviceList);
                            break;
                        }
                        if (i >= deviceList.size() - 1) {
                            deviceList.add(new FeasyDevice(result.getDevice().getAddress(), result.getScanRecord().getServiceData(result.getScanRecord().getServiceUuids().get(0))));
                            view.showBle(deviceList);
                        }
                    }
                } else {
                    deviceList.add(new FeasyDevice(result.getDevice().getAddress(), result.getScanRecord().getServiceData(result.getScanRecord().getServiceUuids().get(0))));
                    view.showBle(deviceList);
                }
            }
        }
        else {
            if (result.getScanRecord().getDeviceName() != null && result.getScanRecord().getDeviceName().startsWith("``NL")) {
                if (deviceList.isEmpty()) {
                    deviceList.add(new FeasyDevice(result.getDevice().getAddress(), result.getScanRecord().getDeviceName()));
                    view.showBle(deviceList);
                } else {
                    for (int i = 0; i < deviceList.size(); i++) {
                        if (result.getDevice().getAddress().equals(deviceList.get(i).getUUID())) {
                            boolean selected = deviceList.get(i).isSelected();
                            deviceList.set(i, new FeasyDevice(result.getDevice().getAddress(), result.getScanRecord().getDeviceName()));
                            deviceList.get(i).setSelected(selected);
                            view.showBle(deviceList);
                            break;
                        }
                        if (i == deviceList.size() - 1) {
                            deviceList.add(i, new FeasyDevice(result.getDevice().getAddress(), result.getScanRecord().getDeviceName()));
                            view.showBle(deviceList);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void updateScene() {
        MyApplication.getScene().setFixtureNum(MyApplication.getScene().getFixtureNum() + 1);
        model.updateScene(MyApplication.getScene());
    }
}
