package com.example.nanlinkdemo.mvp.model.Impl;


import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.bean.RegisterUser;
import com.example.nanlinkdemo.mvp.model.UserSettingModel;
import com.example.nanlinkdemo.mvp.presenter.Impl.UserSettingPresenterImpl;

import java.util.ArrayList;

public class UserSettingModelImpl implements UserSettingModel {

    private final UserSettingPresenterImpl presenter;
    private ArrayList<Menu> settingArrayList;
    private ArrayList<RegisterUser> userArrayList;

    public UserSettingModelImpl(UserSettingPresenterImpl presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getSettingList() {
        settingArrayList = new ArrayList<>();
        settingArrayList.add(new Menu("编辑账号信息", R.drawable.ic_setting_expand));
        settingArrayList.add(new Menu("修改密码", R.drawable.ic_setting_expand));
        settingArrayList.add(new Menu("退出登录", R.drawable.ic_setting_expand));

        userArrayList = new ArrayList<>();
        userArrayList.add(new RegisterUser("测试者", "977323939@qq.com"));
        presenter.showSettingListToView(settingArrayList, userArrayList);
    }
}
