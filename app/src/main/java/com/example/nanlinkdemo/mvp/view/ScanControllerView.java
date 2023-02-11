package com.example.nanlinkdemo.mvp.view;

import com.example.nanlinkdemo.bean.FeasyDevice;

import java.util.ArrayList;

public interface ScanControllerView {
    void setPresenter();

    void showController(ArrayList<FeasyDevice> arrayList);

    void updateFinishBtn(ArrayList<FeasyDevice> arrayList);

    void finish();

    void startLoading();

    void stopLoading();
}
