package com.example.NanLinkDemo.mvp.presenter;

import android.bluetooth.le.ScanResult;
import android.view.View;

public interface ScanControllerPresenter {
    void handleResult(ScanResult result);

    void onClickSwitch(int position);

    void onClickSwitch(View view);
}
