package com.example.nanlinkdemo.mvp.model.Impl;

import com.example.nanlinkdemo.mvp.model.WebViewModel;
import com.example.nanlinkdemo.mvp.presenter.Impl.WebViewPresenterImpl;
import com.example.nanlinkdemo.util.ApiClient;
import com.example.nanlinkdemo.util.Constant;

public class WebViewModelImpl implements WebViewModel {
    private final WebViewPresenterImpl presenter;

    public WebViewModelImpl(WebViewPresenterImpl presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getMessage(int contentId) {
        switch (contentId){
            case Constant.PRIVACY_POLICY:
                presenter.showMessageToView("隐私条款", ApiClient.PRIVACY_POLICY_URL);
                break;
            case Constant.USER_AGREEMENT:
                presenter.showMessageToView("用户协议", ApiClient.USER_AGREEMENT_URL);
                break;
            case Constant.ABOUT:
                presenter.showMessageToView("有关", ApiClient.ABOUT_URL);
                break;
        }
    }
}
