package com.example.nanlinkdemo.mvp.presenter.Impl;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.nanlinkdemo.DB.bean.User;
import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.bean.RegisterUser;
import com.example.nanlinkdemo.mvp.model.Impl.UserSettingModelImpl;
import com.example.nanlinkdemo.mvp.presenter.UserSettingPresenter;
import com.example.nanlinkdemo.mvp.view.UserSettingView;
import com.example.nanlinkdemo.util.Constant;

import java.util.ArrayList;
import java.util.List;

public class UserSettingPresenterImpl implements UserSettingPresenter {
    private final UserSettingModelImpl model;
    private final UserSettingView view;
    private User onlineUser, lastUser;

    public UserSettingPresenterImpl(UserSettingView view) {
        this.view = view;
        model = new UserSettingModelImpl(this);
    }

    @Override
    public void getSettingListFromView() {
        model.getOnlineUser();
    }

    @Override
    public void showSettingListToView(ArrayList<Menu> settingArrayList, ArrayList<RegisterUser> userArrayList) {
        view.showStories(settingArrayList, userArrayList);
    }

    @Override
    public void settingSwitch(String settingText) {
        switch (settingText){
            case "编辑账号信息":
            case "修改密码":
                view.showMenuDialog(settingText, "该功能还没开发", 0);
                break;
            case "退出登录":
                model.getLastUser();
                break;
        }
    }

    @Override
    public void receiveLastUser(List<User> users) {
        if (!users.isEmpty()){
            lastUser = users.get(0);
            lastUser.setType("normal");
            model.updateUser(lastUser);
        }else {
            onlineUser.setType("lastUser");
            model.updateUser(onlineUser);
        }


    }
    @Override
    public void updateOnlineUser() {

        ARouter.getInstance().build(Constant.ACTIVITY_URL_Login).navigation();
        view.finish();
    }

    @Override
    public void receiveOnlineUser(List<User> users) {
        onlineUser = users.get(0);
        model.getSettingList(onlineUser);


    }
}
