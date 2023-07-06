package com.example.NanLinkDemo.mvp.view;

import com.example.NanLinkDemo.bean.Device;

import java.util.ArrayList;

public interface ScanControllerView {
    void setPresenter();

    void showController(ArrayList<Device> arrayList);

    void updateFinishBtn(ArrayList<Device> arrayList);

    void finish();

    void startLoading();

    void stopLoading();
}
