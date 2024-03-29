package com.example.NanLinkDemo.mvp.presenter.Impl;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.bean.Menu;
import com.example.NanLinkDemo.mvp.model.Impl.SettingModelImpl;
import com.example.NanLinkDemo.mvp.presenter.SettingPresenter;
import com.example.NanLinkDemo.mvp.view.SettingView;
import com.example.NanLinkDemo.ui.MyDialog;
import com.example.NanLinkDemo.util.Constant;

import java.util.ArrayList;

public class SettingPresenterImpl implements SettingPresenter {
    private final SettingModelImpl model;
    private final SettingView view;

    public SettingPresenterImpl(SettingView view) {
        this.view = view;
        model = new SettingModelImpl(this);
    }

    @Override
    public void getListDataFromView() {
        model.getSettingList();
    }

    @Override
    public void showSettingListToView(ArrayList<Menu> settingArrayList) {
        view.showStories(settingArrayList);
    }

    @Override
    public void onClickSwitch(String settingText) {
        switch (settingText){
            case "账号设置":
                ARouter.getInstance().build(Constant.ACTIVITY_URL_UserSetting).navigation();
                break;
            case "语言设置":
                view.showMyDialog(MyDialog.Read_OneBtn_NormalTitle_BlueOneBtn, settingText, "该功能还没开发", "重试", null);
                break;
            case "意见反馈":
                ARouter.getInstance().build(Constant.ACTIVITY_URL_Feedback).navigation();
                break;
            case "防止休眠":
                MyApplication.getOnlineUser().setKeepScreenOn(!MyApplication.getOnlineUser().isKeepScreenOn());
                model.updateUser(MyApplication.getOnlineUser());
                ARouter.getInstance().build(Constant.ACTIVITY_URL_Setting).navigation();
                view.finish();
                break;
        }
    }
}
