package com.example.nanlinkdemo.mvp.view;

import com.example.nanlinkdemo.bean.FeasyDevice;

import java.util.ArrayList;

public interface ScanBleView {
    void setPresenter();

    void showBle(ArrayList<FeasyDevice> arrayList);

    void finish();
}
