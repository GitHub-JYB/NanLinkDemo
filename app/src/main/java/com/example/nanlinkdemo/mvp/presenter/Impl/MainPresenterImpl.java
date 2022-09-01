package com.example.nanlinkdemo.mvp.presenter.Impl;

import android.app.Activity;
import android.view.View;

import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.mvp.model.Impl.MainModelImpl;
import com.example.nanlinkdemo.mvp.presenter.MainPresenter;
import com.example.nanlinkdemo.mvp.view.MainView;
import com.example.nanlinkdemo.util.SnackBarUtil;

import java.util.ArrayList;

public class MainPresenterImpl implements MainPresenter {

    private final MainView mainView;
    private final MainModelImpl mainModelImpl;


    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
        mainModelImpl = new MainModelImpl(this);
    }

    @Override
    public void getMenuFromModel() {
        mainModelImpl.getMenu();
    }

    @Override
    public void showMenuToView(ArrayList<Menu> menuArrayList) {
        mainView.showMenu(menuArrayList);
    }

    @Override
    public void menuSwitch(String menuText) {
        SnackBarUtil.show((Activity) mainView, menuText);

    }

    @Override
    public void toolbarSwitch(View v) {
        switch (v.getId()){
            case R.id.toolbar_left_btn:
                mainView.openDrawLayout();
        }
    }
}
