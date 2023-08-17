package com.example.NanLinkDemo.mvp.view;

public interface CameraView {
    void setPresenter();

    void finish();

    void stopPreview();

    void updateBtn(boolean isPreviewing);

    void startPreview();

    void toggleZoom();

    int getHSI();

    int getSAT();
}
