package com.example.NanLinkDemo.mvp.view;

import com.example.NanLinkDemo.bean.Device;
import com.example.NanLinkDemo.ui.MyDialog;

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

    void updateRightBtn(boolean able);

    void showMyDialog(int type, String title, String message, String negativeText, MyDialog.NegativeOnClickListener negativeListener, String positiveText, MyDialog.PositiveOnClickListener positiveListener);

    void showMyDialog(int type, String title, String bigSizeMessage, String smallSizeMessage, String negativeText, MyDialog.NegativeOnClickListener negativeListener, String positiveText, MyDialog.PositiveOnClickListener positiveListener);

    void dismissMyDialog();

    String getInputTextMyDialog();

}
