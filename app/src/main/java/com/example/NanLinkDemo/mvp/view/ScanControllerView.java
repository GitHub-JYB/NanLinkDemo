package com.example.NanLinkDemo.mvp.view;

import com.example.NanLinkDemo.bean.FeasyDevice;

import java.util.ArrayList;

public interface ScanControllerView {
    void setPresenter();

    void showController(ArrayList<FeasyDevice> arrayList);

    void updateFinishBtn(ArrayList<FeasyDevice> arrayList);

    void finish();

    void startLoading();

    void stopLoading();
}
