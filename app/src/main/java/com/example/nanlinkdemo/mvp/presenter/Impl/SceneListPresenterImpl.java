package com.example.nanlinkdemo.mvp.presenter.Impl;

import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.mvp.model.Impl.SceneListModelImpl;
import com.example.nanlinkdemo.mvp.presenter.SceneListPresenter;
import com.example.nanlinkdemo.mvp.view.SceneListView;

import java.util.ArrayList;

public class SceneListPresenterImpl implements SceneListPresenter {

    private final SceneListView view;
    private final SceneListModelImpl model;

    public SceneListPresenterImpl(SceneListView view) {
        this.view = view;
        model = new SceneListModelImpl(this);
    }

    @Override
    public void showMenuToView(ArrayList<Menu> menuArrayList) {
        view.showStories(menuArrayList);
    }

    @Override
    public void getMenuFromView() {
        model.getMenu();
    }
}
