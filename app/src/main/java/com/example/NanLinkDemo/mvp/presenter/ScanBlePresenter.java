package com.example.NanLinkDemo.mvp.presenter;

import android.view.View;

import no.nordicsemi.android.support.v18.scanner.ScanResult;

public interface ScanBlePresenter {
    void onClickSwitch(int position);

    void getListDataFromView();

    void onClickSwitch(View view);

    void handleResult(ScanResult result);

    void updateScene();
}
