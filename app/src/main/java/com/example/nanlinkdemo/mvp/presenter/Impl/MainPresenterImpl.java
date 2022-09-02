package com.example.nanlinkdemo.mvp.presenter.Impl;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;

import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.mvp.model.Impl.MainModelImpl;
import com.example.nanlinkdemo.mvp.presenter.MainPresenter;
import com.example.nanlinkdemo.mvp.view.MainView;
import com.example.nanlinkdemo.ui.readDialog;
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
//        SnackBarUtil.show((Activity) mainView, menuText);
        mainView.showMenuDialog(menuText);
    }

    @Override
    public void toolbarSwitch(View v) {
        switch (v.getId()){
            case R.id.toolbar_left_btn:
                mainView.openDrawLayout();
        }
    }
}
