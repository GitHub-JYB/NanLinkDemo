package com.example.nanlinkdemo.mvp.presenter.Impl;

import android.view.View;

import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.mvp.model.Impl.MainModelImpl;
import com.example.nanlinkdemo.mvp.presenter.MainPresenter;
import com.example.nanlinkdemo.mvp.view.MainView;
import com.example.nanlinkdemo.mvp.widget.SceneListFragment;
import com.example.nanlinkdemo.mvp.widget.SettingFragment;
import com.example.nanlinkdemo.mvp.widget.UserSettingFragment;

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
//        SnackBarUtil.show((Activity) mainView, menuText);
        view.closeDrawLayout();
        switch (menuText){
            case "测试者":
                view.closeDrawLayout();
                view.setToolbar(R.drawable.ic_back, "账号设置", 0, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view.initToolbar();
                        view.replaceFragment(UserSettingFragment.getInstance(), SceneListFragment.getInstance());
                    }
                }, null);
                view.replaceFragment(SceneListFragment.getInstance(), UserSettingFragment.getInstance());
                break;
            case "创建场景":
                view.showMenuDialog(menuText, "", 1);
                break;
            case "创建场景群组":
                view.showMenuDialog(menuText, "", 1);
                break;
            case "排序":
                view.showMenuDialog(menuText, "该功能还没开发", 0);
                break;
            case "设置":
                view.closeDrawLayout();
                view.setToolbar(R.drawable.ic_back, "设置", 0, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view.initToolbar();
                        view.replaceFragment(SettingFragment.getInstance(), SceneListFragment.getInstance());
                    }
                }, null);
                view.replaceFragment(SceneListFragment.getInstance(), SettingFragment.getInstance());
                break;
            case "有关":
                view.showMenuDialog(menuText, "该功能还没开发", 0);
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
