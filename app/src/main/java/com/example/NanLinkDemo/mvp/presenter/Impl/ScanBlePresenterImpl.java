package com.example.NanLinkDemo.mvp.presenter.Impl;


import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.view.View;


import com.alibaba.android.arouter.launcher.ARouter;
import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.bean.Device;
import com.example.NanLinkDemo.mvp.model.Impl.ScanBleModelImpl;
import com.example.NanLinkDemo.mvp.presenter.ScanBlePresenter;
import com.example.NanLinkDemo.mvp.view.ScanBleView;
import com.example.NanLinkDemo.util.Constant;

import java.util.ArrayList;


public class ScanBlePresenterImpl implements ScanBlePresenter {
    private final ScanBleView view;
    private final ScanBleModelImpl model;

    private boolean allSelected = false;

    ArrayList<Device> deviceList = new ArrayList<>();
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
                for (Device device : deviceList) {
                    device.setSelected(!allSelected);
                }
                view.showBle(deviceList);
                break;
            case R.id.complete:
                view.startLoading();
                for (Device device : deviceList) {
                    if (device.isSelected()) {
                        model.addBleFixture(device);
                    }
                }
                view.stopLoading();
                ARouter.getInstance().build(Constant.ACTIVITY_URL_Scene).withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).navigation();
                view.finish();
                break;
            case R.id.toolbar_right_btn:
                deviceList.clear();
                view.startScan();
        }
    }

    @Override
    public void handleResult(ScanResult result) {
        ScanRecord scanRecord = result.getScanRecord();
        if (scanRecord != null){
            if (scanRecord.getBytes() != null){
                if (scanRecord.getServiceData() != null & scanRecord.getServiceUuids() != null) {
                    byte[] uuid = scanRecord.getServiceData().get(scanRecord.getServiceUuids().get(0));
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
                                    deviceList.set(i, new Device(result.getDevice().getAddress(), result.getScanRecord().getServiceData(result.getScanRecord().getServiceUuids().get(0))));
                                    deviceList.get(i).setSelected(selected);
                                    view.showBle(deviceList);
                                    break;
                                }
                                if (i >= deviceList.size() - 1) {
                                    deviceList.add(new Device(result.getDevice().getAddress(), result.getScanRecord().getServiceData(result.getScanRecord().getServiceUuids().get(0))));
                                    view.showBle(deviceList);
                                }
                            }
                        } else {
                            deviceList.add(new Device(result.getDevice().getAddress(), result.getScanRecord().getServiceData(result.getScanRecord().getServiceUuids().get(0))));
                            view.showBle(deviceList);
                        }
                    }
                }
                else {
                    if (scanRecord.getDeviceName() != null && scanRecord.getDeviceName().startsWith("``NL")) {
                        if (deviceList.isEmpty()) {
                            deviceList.add(new Device(result.getDevice().getAddress(), result.getScanRecord().getDeviceName()));
                            view.showBle(deviceList);
                        } else {
                            for (int i = 0; i < deviceList.size(); i++) {
                                if (result.getDevice().getAddress().equals(deviceList.get(i).getUUID())) {
                                    boolean selected = deviceList.get(i).isSelected();
                                    deviceList.set(i, new Device(result.getDevice().getAddress(), result.getScanRecord().getDeviceName()));
                                    deviceList.get(i).setSelected(selected);
                                    view.showBle(deviceList);
                                    break;
                                }
                                if (i == deviceList.size() - 1) {
                                    deviceList.add(i, new Device(result.getDevice().getAddress(), result.getScanRecord().getDeviceName()));
                                    view.showBle(deviceList);
                                }
                            }
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
