package com.example.nanlinkdemo.mvp.presenter.Impl;

import static com.example.nanlinkdemo.util.Constant.ACTIVITY_URL_Main;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;

import androidx.core.app.ActivityCompat;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.DB.bean.User;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.DeviceMessage;
import com.example.nanlinkdemo.bean.Message;
import com.example.nanlinkdemo.mvp.model.Impl.LoginModelImpl;
import com.example.nanlinkdemo.mvp.presenter.LoginPresenter;
import com.example.nanlinkdemo.mvp.view.LoginView;
import com.example.nanlinkdemo.mvp.widget.LoginActivity;
import com.example.nanlinkdemo.ui.MyDialog;
import com.example.nanlinkdemo.util.Constant;
import com.example.nanlinkdemo.util.SnackBarUtil;

import java.util.ArrayList;
import java.util.List;


public class LoginPresenterImpl implements LoginPresenter {

    private final LoginView view;
    private final LoginModelImpl model;
    private boolean checked = false;
    private String email, password;

    @Override
    public void sendMesToView(Message mes) {
        view.stopLoading();
        switch (mes.getCode()){
            case 200:
                MyApplication.setOnlineUser(new User(mes.getData().getEmail(), mes.getData().getNickName(), mes.getData().getVocation(), "online", mes.getData().getToken()));
                model.queryEmail(MyApplication.getOnlineUser().getEmail());
                break;
            case 1001:
            case 1002:
            case 1003:
            case 1004:
            case 1005:
            case 1006:
            case 1007:
            case 1008:
            case 1009:
            case 1010:
            case 1011:
                view.showMyDialog(MyDialog.Read_OneBtn_WarningTitle_BlueOneBtn,"错误", mes.getMsg().toString(),"重试", null);
                break;
        }
    }


    public LoginPresenterImpl(LoginView loginView) {
        this.view = loginView;
        this.model = new LoginModelImpl(this);
    }

    @Override
    public void switchOnclick(View view) {
        switch (view.getId()){
            case R.id.btn_register:
                ARouter.getInstance().build(Constant.ACTIVITY_URL_Register).navigation();
                break;
            case R.id.login_check:
                if (checked){
                    this.view.setCheckImage(R.drawable.unchecked);
                    checked = false;
                }else {
                    this.view.setCheckImage(R.drawable.checked);
                    checked = true;
                }
                break;
            case R.id.btn_login:
                email = this.view.getEmail();
                password = this.view.getPassword();
                if (email.isEmpty()){
                    SnackBarUtil.show(view, "请输入邮箱");
                }else if (password.isEmpty()){
                    SnackBarUtil.show(view,"请输入密码");
                }else if (password.length() < 6 || password.length() > 20){
                    SnackBarUtil.show(view, "请输入6-20位密码");
                }else if (checked){
                    if (!MyApplication.getInstance().isOpenNetwork()){
                        this.view.showMyDialog(MyDialog.Read_OneBtn_WarningTitle_BlueOneBtn,"错误", "无法连接服务器","重试", null);
                    }else {
                        this.view.startLoading();
                        model.login(email, password);
                    }
                }else {
                    SnackBarUtil.show(view,"请勾选用户协议");
                }
                break;
        }
    }


    @Override
    public void receiveUser(List<User> users) {
        if (!users.isEmpty()){
            MyApplication.getOnlineUser().setId(users.get(0).getId());
            MyApplication.getOnlineUser().setKeepScreenOn(users.get(0).isKeepScreenOn());
            model.updateUser(MyApplication.getOnlineUser());
        }else {
            model.addUser(MyApplication.getOnlineUser());
        }
        ARouter.getInstance().build(ACTIVITY_URL_Main).navigation();
        view.finish();
    }


}
