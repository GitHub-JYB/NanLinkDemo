package com.example.nanlinkdemo.mvp.model.Impl;

import com.example.nanlinkdemo.mvp.model.SceneGroupModel;
import com.example.nanlinkdemo.mvp.presenter.Impl.SceneGroupPresenterImpl;

public class SceneGroupModelImpl implements SceneGroupModel {
    private final SceneGroupPresenterImpl presenter;

    public SceneGroupModelImpl(SceneGroupPresenterImpl presenter) {
        this.presenter = presenter;
    }
}
