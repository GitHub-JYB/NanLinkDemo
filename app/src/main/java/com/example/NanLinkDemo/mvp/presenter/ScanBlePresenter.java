package com.example.NanLinkDemo.mvp.presenter;

import android.bluetooth.le.ScanResult;
import android.view.View;

import com.example.NanLinkDemo.bean.Device;

public interface ScanBlePresenter {
    void onClickSwitch(int position);

    void getListDataFromView();

    void onClickSwitch(View view);

    void handleResult(ScanResult result);

    void updateScene();
}
