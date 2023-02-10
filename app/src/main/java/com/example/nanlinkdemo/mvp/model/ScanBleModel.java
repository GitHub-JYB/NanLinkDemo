package com.example.nanlinkdemo.mvp.model;

import com.example.nanlinkdemo.DB.bean.Scene;
import com.example.nanlinkdemo.bean.FeasyDevice;

public interface ScanBleModel {
    void getListData();

    void addBleFixture(FeasyDevice device);

    void updateScene(Scene scene);
}
