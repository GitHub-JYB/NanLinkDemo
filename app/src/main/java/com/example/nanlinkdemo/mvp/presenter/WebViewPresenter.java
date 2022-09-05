package com.example.nanlinkdemo.mvp.presenter;

public interface WebViewPresenter {
    void SwitchContentId(int contentId);

    void showMessageToView(String title, String url);
}
