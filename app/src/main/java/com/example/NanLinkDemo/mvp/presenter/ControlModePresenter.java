package com.example.NanLinkDemo.mvp.presenter;

public interface ControlModePresenter {
    void updateDim(int position, String dim);

    void getDataList();

    void clickDelayTime(int position, String delayTime);

    void clickData(int position, String dim);
}
