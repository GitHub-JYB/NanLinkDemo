package com.example.NanLinkDemo.mvp.view;

import com.example.NanLinkDemo.bean.Device;

import java.util.ArrayList;

public interface ScanBleView {
    void startScan();

    void setPresenter();

    void showBle(ArrayList<Device> arrayList);

    void finish();

    void updateAllSelectedBtn(ArrayList<Device> arrayList);

    void updateFinishBtn(ArrayList<Device> arrayList);

    void updateAllSelectedText(boolean allSelected);

    void startLoading();

    void stopLoading();

    void startScanAnimation();

    void stopScanAnimation();

    void updateRightBtnClickable(boolean able);
}
