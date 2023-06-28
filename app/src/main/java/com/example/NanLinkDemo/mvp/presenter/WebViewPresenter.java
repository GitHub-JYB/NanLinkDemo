package com.example.NanLinkDemo.mvp.presenter;

public interface WebViewPresenter {
    void SwitchContentId(int contentId);

    void showMessageToView(String title, String url);
}
