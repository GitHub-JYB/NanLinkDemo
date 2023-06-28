package com.example.NanLinkDemo.mvp.view;

import com.example.NanLinkDemo.bean.FeasyDevice;

import java.util.ArrayList;

public interface ScanBleView {
    void StartScan();

    void removeHandler();

    void setPresenter();

    void showBle(ArrayList<FeasyDevice> arrayList);

    void finish();

    void updateAllSelectedBtn(ArrayList<FeasyDevice> arrayList);

    void updateFinishBtn(ArrayList<FeasyDevice> arrayList);

    void updateAllSelectedText(boolean allSelected);

    void startLoading();

    void stopLoading();

    void startScanAnimation();

    void stopScanAnimation();

    void updateRightBtnClickable(boolean able);
}
