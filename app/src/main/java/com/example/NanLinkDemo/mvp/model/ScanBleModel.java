package com.example.NanLinkDemo.mvp.model;

import com.example.NanLinkDemo.DB.bean.Scene;
import com.example.NanLinkDemo.bean.Device;

public interface ScanBleModel {
    void getListData();

    void addBleFixture(Device device);

    void updateScene(Scene scene);
}
