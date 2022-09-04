package com.example.nanlinkdemo.mvp.model.Impl;


import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.mvp.model.SettingModel;
import com.example.nanlinkdemo.mvp.presenter.Impl.SettingPresenterImpl;

import java.util.ArrayList;

public class SettingModelImpl implements SettingModel {

    private final SettingPresenterImpl presenter;
    private ArrayList<Menu> settingArrayList;

    public SettingModelImpl(SettingPresenterImpl presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getSettingList() {
        settingArrayList = new ArrayList<>();
        settingArrayList.add(new Menu("账号设置", R.drawable.ic_setting_expand));
        settingArrayList.add(new Menu("语言设置", R.drawable.ic_setting_expand));
        settingArrayList.add(new Menu("意见反馈", R.drawable.ic_setting_expand));
        settingArrayList.add(new Menu("防止休眠", R.drawable.ic_setting_switch_close));

        presenter.showSettingListToView(settingArrayList);
    }
}
