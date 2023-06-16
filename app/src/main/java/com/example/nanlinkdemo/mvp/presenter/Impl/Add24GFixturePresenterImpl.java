package com.example.nanlinkdemo.mvp.presenter.Impl;

import com.example.nanlinkdemo.mvp.model.Impl.Add24GFixtureModelImpl;
import com.example.nanlinkdemo.mvp.presenter.Add24GFixturePresenter;
import com.example.nanlinkdemo.mvp.view.Add24GFixtureView;

public class Add24GFixturePresenterImpl implements Add24GFixturePresenter {
    private final Add24GFixtureView view;
    private final Add24GFixtureModelImpl model;

    public Add24GFixturePresenterImpl(Add24GFixtureView view) {
        this.view = view;
        this.model = new Add24GFixtureModelImpl(this);
    }
}
