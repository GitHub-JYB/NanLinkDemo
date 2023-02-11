package com.example.nanlinkdemo.mvp.presenter.Impl;


import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.view.View;


import com.alibaba.android.arouter.launcher.ARouter;
import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.FeasyDevice;
import com.example.nanlinkdemo.mvp.model.Impl.ScanBleModelImpl;
import com.example.nanlinkdemo.mvp.presenter.ScanBlePresenter;
import com.example.nanlinkdemo.mvp.view.ScanBleView;
import com.example.nanlinkdemo.util.Constant;

import java.util.ArrayList;
import java.util.Arrays;


public class ScanBlePresenterImpl implements ScanBlePresenter {
    private final ScanBleView view;
    private final ScanBleModelImpl model;

    private boolean allSelected = false;

    ArrayList<byte[]> uuidList = new ArrayList<>();
    ArrayList<FeasyDevice> deviceList = new ArrayList<>();

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
        switch (v.getId()){
            case R.id.toolbar_left_btn:
                view.finish();
                break;
            case R.id.all_selected:
                view.updateAllSelectedText(!allSelected);
                for (FeasyDevice device:deviceList){
                    device.setSelected(!allSelected);
                }
                allSelected = !allSelected;
                view.showBle(deviceList);
                break;
            case R.id.complete:
                view.startLoading();
                for (FeasyDevice device : deviceList) {
                    if (device.isSelected()){
                        model.addBleFixture(device);
                    }
                }
                view.stopLoading();
                ARouter.getInstance().build(Constant.ACTIVITY_URL_Scene).withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).navigation();
                view.finish();
                break;
        }
    }

    @Override
    public void handleResult(ScanResult result) {
        if (result.getScanRecord().getServiceData() != null & result.getScanRecord().getServiceUuids() != null) {
            byte[] uuid = result.getScanRecord().getServiceData().get(result.getScanRecord().getServiceUuids().get(0));
            if ((uuid != null ? uuid.length : 0) > 14){
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
                if (String.format("%08X", uuid[13]).charAt(String.format("%08X", uuid[13]).length() - 1) == '1'){
                    return;
                }
                if (uuidList.size() != 0) {
                    for (int i = 0; i < uuidList.size(); i++) {
                        if (Arrays.equals(uuidList.get(i), uuid)) {
                            return;
                        }
                        if (Arrays.equals(Arrays.copyOfRange(uuid, 0, 6), Arrays.copyOfRange(uuidList.get(i), 0, 6))) {
                            uuidList.set(i, uuid);
                            deviceList.set(i, new FeasyDevice(uuid));
                            view.showBle(deviceList);
                            break;
                        }
                        if (i == uuidList.size() - 1) {
                            uuidList.add(uuid);
                            deviceList.add(new FeasyDevice(uuid));
                            view.showBle(deviceList);
                        }
                    }
                } else {
                    uuidList.add(uuid);
                    deviceList.add(new FeasyDevice(uuid));
                    view.showBle(deviceList);
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
