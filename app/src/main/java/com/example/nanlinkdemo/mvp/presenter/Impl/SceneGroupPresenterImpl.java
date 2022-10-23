package com.example.nanlinkdemo.mvp.presenter.Impl;

import com.example.nanlinkdemo.mvp.model.Impl.SceneGroupModelImpl;
import com.example.nanlinkdemo.mvp.presenter.SceneGroupPresenter;
import com.example.nanlinkdemo.mvp.view.SceneGroupView;
import com.example.nanlinkdemo.mvp.widget.SceneGroupActivity;

public class SceneGroupPresenterImpl implements SceneGroupPresenter {
    private final SceneGroupView view;
    private final SceneGroupModelImpl model;

    public SceneGroupPresenterImpl(SceneGroupView view) {
        this.view = view;
        this.model = new SceneGroupModelImpl(this);
    }
}
