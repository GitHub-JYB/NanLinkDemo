package com.example.nanlinkdemo.mvp.presenter.Impl;

import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.mvp.model.Impl.SettingModelImpl;
import com.example.nanlinkdemo.mvp.presenter.SettingPresenter;
import com.example.nanlinkdemo.mvp.view.SettingView;

import java.util.ArrayList;

public class SettingPresenterImpl implements SettingPresenter {
    private final SettingModelImpl model;
    private final SettingView view;

    public SettingPresenterImpl(SettingView view) {
        this.view = view;
        model = new SettingModelImpl(this);
    }

    @Override
    public void getSettingListFromView() {
        model.getSettingList();
    }

    @Override
    public void showSettingListToView(ArrayList<Menu> settingArrayList) {
        view.showStories(settingArrayList);
    }
}
