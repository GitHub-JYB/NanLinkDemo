package com.example.nanlinkdemo.mvp.model.Impl;

import com.example.nanlinkdemo.mvp.model.AboutModel;
import com.example.nanlinkdemo.mvp.presenter.Impl.AboutPresenterImpl;

public class AboutModelImpl implements AboutModel {
    private final AboutPresenterImpl presenter;

    public AboutModelImpl(AboutPresenterImpl presenter) {
        this.presenter = presenter;
    }
}
