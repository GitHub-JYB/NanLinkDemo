package com.example.NanLinkDemo.mvp.presenter.Impl;

import static com.example.NanLinkDemo.util.Constant.ACTIVITY_URL_Main;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.bean.User;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.bean.Message;
import com.example.NanLinkDemo.mvp.model.Impl.LoginModelImpl;
import com.example.NanLinkDemo.mvp.presenter.LoginPresenter;
import com.example.NanLinkDemo.mvp.view.LoginView;
import com.example.NanLinkDemo.ui.MyDialog;
import com.example.NanLinkDemo.util.Constant;
import com.example.NanLinkDemo.util.SnackBarUtil;

import java.util.List;


public class LoginPresenterImpl implements LoginPresenter {

    private final LoginView view;
    private final LoginModelImpl model;
    private boolean checked = false;
    private String email, password;
    private Message.Data loginData;

    @Override
    public void sendMesToView(Message mes) {
        switch (mes.getCode()) {
            case 200:
                view.stopLoading();
                loginData = mes.getData();
                model.queryEmail(mes.getData().getEmail());
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
                showWarnToView();
                break;
        }
    }

    @Override
    public void showWarnToView(){
        view.stopLoading();
        view.showMyDialog(MyDialog.Read_OneBtn_WarningTitle_BlueOneBtn, "错误", "无法连接服务器", "重试", null);
    }

    public LoginPresenterImpl(LoginView loginView) {
        this.view = loginView;
        this.model = new LoginModelImpl(this);
    }

    @Override
    public void switchOnclick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                ARouter.getInstance().build(Constant.ACTIVITY_URL_Register).navigation();
                break;
            case R.id.login_check:
                if (checked) {
                    this.view.setCheckImage(R.drawable.unchecked);
                    checked = false;
                } else {
                    this.view.setCheckImage(R.drawable.checked);
                    checked = true;
                }
                break;
            case R.id.btn_login:
                email = this.view.getEmail();
                password = this.view.getPassword();
                if (email.isEmpty()) {
                    SnackBarUtil.show(view, "请输入邮箱");
                } else if (password.isEmpty()) {
                    SnackBarUtil.show(view, "请输入密码");
                } else if (password.length() < 6 || password.length() > 20) {
                    SnackBarUtil.show(view, "请输入6-20位密码");
                } else if (checked) {
                    this.view.startLoading();
                    if (!MyApplication.getInstance().isOpenNetwork()) {
                        showWarnToView();
                    } else {
                        model.login(email, password);
                    }
                } else {
                    SnackBarUtil.show(view, "请勾选用户协议");
                }
                break;
            case R.id.tv_forgetPassword:
                if (checked) {
                    model.queryEmail("Guest");
                } else {
                    SnackBarUtil.show(view, "请勾选用户协议");
                    this.view.initForgetPassword();
                }
                break;
        }
    }


    @Override
    public void receiveUser(List<User> users) {
        if (!users.isEmpty()) {
            User user = users.get(0);
            MyApplication.setOnlineUser(users.get(0));
            if (!user.getEmail().equals("Guest")){
                user.setNickName(loginData.getNickName());
                user.setVocation(loginData.getVocation());
                user.setToken(loginData.getToken());
            }
            user.setType("online");
            MyApplication.setOnlineUser(user);
            model.updateUser(user);
        } else {
            model.addUser(new User("Guest", "访客模式", "", "online", ""));
        }
        ARouter.getInstance().build(ACTIVITY_URL_Main).navigation();
        view.finish();
    }

    @Override
    public void getLastUserFromModel() {
        model.getLastUser();
    }

    @Override
    public void receiveLastUser(List<User> users) {
        if (!users.isEmpty()) {
            MyApplication.setLastUser(users.get(0));
            if (!users.get(0).getEmail().equals("Guest")) {
                view.updateEmail(users.get(0).getEmail());
            }
        }
    }


}
