package com.example.NanLinkDemo.mvp.presenter.Impl;

import android.content.Intent;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.bean.Menu;
import com.example.NanLinkDemo.bean.RegisterUser;
import com.example.NanLinkDemo.mvp.model.Impl.UserSettingModelImpl;
import com.example.NanLinkDemo.mvp.presenter.UserSettingPresenter;
import com.example.NanLinkDemo.mvp.view.UserSettingView;
import com.example.NanLinkDemo.mvp.widget.ResetPasswordActivity;
import com.example.NanLinkDemo.ui.MyDialog;
import com.example.NanLinkDemo.util.Constant;

import java.util.ArrayList;

public class UserSettingPresenterImpl implements UserSettingPresenter {
    private final UserSettingModelImpl model;
    private final UserSettingView view;

    public UserSettingPresenterImpl(UserSettingView view) {
        this.view = view;
        model = new UserSettingModelImpl(this);
    }


    @Override
    public void showSettingListToView(ArrayList<Menu> settingArrayList, ArrayList<RegisterUser> userArrayList) {
        view.showStories(settingArrayList, userArrayList);
    }

    @Override
    public void settingSwitch(String settingText) {
        switch (settingText){
            case "编辑账号信息":
                ARouter.getInstance().build(Constant.ACTIVITY_URL_EditUserInfo).navigation();
                break;
            case "修改密码":
                ARouter.getInstance().build(Constant.ACTIVITY_URL_ResetPassword).withString("email", MyApplication.getOnlineUser().getEmail()).withInt("type", ResetPasswordActivity.Type_Change_Password).navigation();
                break;
            case "退出登录":
                view.showMyDialog(MyDialog.Read_TwoBtn_NormalTitle_WhiteTwoBtn, settingText, "是否要退出登录?", "取消", null, "退出登录", new MyDialog.PositiveOnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clearLoginRecord();
                        ARouter.getInstance().build(Constant.ACTIVITY_URL_Login).navigation();
                        view.finish();
                        }
                });
                break;
            case "注册":
                clearLoginRecord();
                ARouter.getInstance().build(Constant.ACTIVITY_URL_Register).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).navigation();
                view.finish();
                break;
            case "登录":
                clearLoginRecord();
                ARouter.getInstance().build(Constant.ACTIVITY_URL_Login).navigation();
                view.finish();
                break;
        }
    }

    private void clearLoginRecord() {
        if (MyApplication.getLastUser() != null){
            MyApplication.getLastUser().setType("normal");
            model.updateUser(MyApplication.getLastUser());
            if (MyApplication.getOnlineUser().getEmail().equals("Guest")){
                MyApplication.getOnlineUser().setEmail(MyApplication.getLastUser().getEmail());
            }
        }
        MyApplication.getOnlineUser().setType("lastUser");
        MyApplication.setLastUser(MyApplication.getOnlineUser());
        MyApplication.setOnlineUser(null);
        model.updateUser(MyApplication.getLastUser());
    }

    @Override
    public void getSettingListFromModel() {
        model.getSettingList();
    }

}
