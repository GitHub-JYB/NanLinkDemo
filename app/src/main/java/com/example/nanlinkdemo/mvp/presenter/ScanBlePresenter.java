package com.example.nanlinkdemo.mvp.presenter;

import android.view.View;

public interface ScanBlePresenter {
    void onClickSwitch(int position);

    void getListDataFromView();

    void onClickSwitch(View view);
}
