package com.example.nanlinkdemo.mvp.presenter.Impl;

import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.bean.RegisterUser;
import com.example.nanlinkdemo.mvp.model.Impl.UserSettingModelImpl;
import com.example.nanlinkdemo.mvp.presenter.UserSettingPresenter;
import com.example.nanlinkdemo.mvp.view.UserSettingView;

import java.util.ArrayList;

public class UserSettingPresenterImpl implements UserSettingPresenter {
    private final UserSettingModelImpl model;
    private final UserSettingView view;

    public UserSettingPresenterImpl(UserSettingView view) {
        this.view = view;
        model = new UserSettingModelImpl(this);
    }

    @Override
    public void getSettingListFromView() {
        model.getSettingList();
    }

    @Override
    public void showSettingListToView(ArrayList<Menu> settingArrayList, ArrayList<RegisterUser> userArrayList) {
        view.showStories(settingArrayList, userArrayList);
    }

    @Override
    public void settingSwitch(String settingText) {
        switch (settingText){
            case "账号设置":
                break;
            case "语言设置":
                break;
            case "意见反馈":
                break;
            case "防止设备休眠":
                break;

        }
    }
}
