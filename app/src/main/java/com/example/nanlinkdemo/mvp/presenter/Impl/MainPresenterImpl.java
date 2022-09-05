package com.example.nanlinkdemo.mvp.presenter.Impl;


import android.app.Activity;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.mvp.model.Impl.MainModelImpl;
import com.example.nanlinkdemo.mvp.presenter.MainPresenter;
import com.example.nanlinkdemo.mvp.view.MainView;
import com.example.nanlinkdemo.mvp.widget.WebViewActivity;
import com.example.nanlinkdemo.util.ApiClient;
import com.example.nanlinkdemo.util.Constant;

import java.util.ArrayList;

public class MainPresenterImpl implements MainPresenter {

    private final MainView view;
    private final MainModelImpl model;


    public MainPresenterImpl(MainView mainView) {
        this.view = mainView;
        model = new MainModelImpl(this);
    }

    @Override
    public void getMenuFromModel() {
        model.getMenu();
    }

    @Override
    public void showMenuToView(ArrayList<Menu> menuArrayList) {
        view.showMenu(menuArrayList);
    }

    @Override
    public void menuSwitch(String menuText) {
        view.closeDrawLayout();
        switch (menuText){
            case "测试者":
                ARouter.getInstance().build(Constant.ACTIVITY_URL_UserSetting).navigation();
                break;
            case "创建场景":
            case "创建场景群组":
                view.showMenuDialog(menuText, "", 1);
                break;
            case "排序":
                view.showMenuDialog(menuText, "该功能还没开发", 0);
                break;
            case "设置":
                ARouter.getInstance().build(Constant.ACTIVITY_URL_Setting).navigation();
                break;
            case "有关":
                ARouter.getInstance().build(Constant.ACTIVITY_URL_WebView).withInt("contentId", Constant.ABOUT).navigation();
                break;
        }
    }

    @Override
    public void toolbarSwitch(View v) {
        switch (v.getId()){
            case R.id.toolbar_left_btn:
                view.openDrawLayout();
        }
    }
}
