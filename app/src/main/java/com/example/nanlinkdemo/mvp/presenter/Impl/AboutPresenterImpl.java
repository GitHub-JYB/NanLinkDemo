package com.example.nanlinkdemo.mvp.presenter.Impl;

import com.example.nanlinkdemo.mvp.model.Impl.AboutModelImpl;
import com.example.nanlinkdemo.mvp.presenter.AboutPresenter;
import com.example.nanlinkdemo.mvp.view.AboutView;
import com.example.nanlinkdemo.mvp.widget.AboutFragment;

public class AboutPresenterImpl implements AboutPresenter {
    private final AboutView view;
    private final AboutModelImpl model;

    public AboutPresenterImpl(AboutView view) {
        this.view = view;
        model = new AboutModelImpl(this);
    }
}
