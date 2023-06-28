package com.example.NanLinkDemo.mvp.model;

import com.example.NanLinkDemo.DB.bean.Scene;
import com.example.NanLinkDemo.bean.FeasyDevice;

public interface ScanBleModel {
    void getListData();

    void addBleFixture(FeasyDevice device);

    void updateScene(Scene scene);
}
