package com.example.NanLinkDemo.mvp.presenter.Impl;


import com.example.NanLinkDemo.mvp.model.Impl.WebViewModelImpl;
import com.example.NanLinkDemo.mvp.presenter.WebViewPresenter;
import com.example.NanLinkDemo.mvp.view.WebViewView;

public class WebViewPresenterImpl implements WebViewPresenter {
    private final WebViewView view;
    private final WebViewModelImpl model;

    public WebViewPresenterImpl(WebViewView view) {
        this.view = view;
        this.model = new WebViewModelImpl(this);
    }

    @Override
    public void SwitchContentId(int contentId) {
        model.getMessage(contentId);

    }

    @Override
    public void showMessageToView(String title, String url) {
        view.showMessage(title,url);
    }
}
