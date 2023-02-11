package com.example.nanlinkdemo.mvp.presenter.Impl;

import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.FeasyDevice;
import com.example.nanlinkdemo.mvp.model.Impl.ScanControllerModelImpl;

import com.example.nanlinkdemo.mvp.presenter.ScanControllerPresenter;
import com.example.nanlinkdemo.mvp.view.ScanControllerView;
import com.example.nanlinkdemo.util.Constant;

import java.util.ArrayList;
import java.util.Arrays;

public class ScanControllerPresenterImpl implements ScanControllerPresenter {
    private final ScanControllerView view;
    private final ScanControllerModelImpl model;


    ArrayList<byte[]> uuidList = new ArrayList<>();
    ArrayList<FeasyDevice> deviceList = new ArrayList<>();

    public ScanControllerPresenterImpl(ScanControllerView view) {
        this.view = view;
        this.model = new ScanControllerModelImpl(this);
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
                if (String.format("%08X", uuid[13]).charAt(String.format("%08X", uuid[13]).length() - 1) == '0'){
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
                            view.showController(deviceList);
                            break;
                        }
                        if (i == uuidList.size() - 1) {
                            uuidList.add(uuid);
                            deviceList.add(new FeasyDevice(uuid));
                            view.showController(deviceList);
                        }
                    }
                } else {
                    uuidList.add(uuid);
                    deviceList.add(new FeasyDevice(uuid));
                    view.showController(deviceList);
                }
            }
        }
    }

    @Override
    public void onClickSwitch(int position) {
        deviceList.get(position).setSelected(!deviceList.get(position).isSelected());
        view.showController(deviceList);
    }

    @Override
    public void onClickSwitch(View v) {
        switch (v.getId()){
            case R.id.toolbar_left_btn:
                view.finish();
                break;
            case R.id.finish:
                view.startLoading();
                for (FeasyDevice device: deviceList) {
                    if (device.isSelected()){
                        model.addController(device);
                    }
                }
                view.stopLoading();
                ARouter.getInstance().build(Constant.ACTIVITY_URL_Controller).withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).navigation();
                view.finish();
                break;
        }
    }
}
