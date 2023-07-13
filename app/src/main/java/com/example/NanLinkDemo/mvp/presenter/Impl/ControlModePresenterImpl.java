package com.example.NanLinkDemo.mvp.presenter.Impl;

import com.example.NanLinkDemo.mvp.model.Impl.ControlModeModelImpl;
import com.example.NanLinkDemo.mvp.presenter.ControlModePresenter;
import com.example.NanLinkDemo.mvp.view.ControlModeView;

public class ControlModePresenterImpl implements ControlModePresenter {
    private final ControlModeView view;
    private final ControlModeModelImpl model;

    public ControlModePresenterImpl(ControlModeView view) {
        this.view = view;
        this.model = new ControlModeModelImpl(this);
    }

}
