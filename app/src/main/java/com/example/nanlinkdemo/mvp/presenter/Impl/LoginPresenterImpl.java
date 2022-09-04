package com.example.nanlinkdemo.mvp.presenter.Impl;

import android.app.Activity;
import android.view.View;

import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Message;
import com.example.nanlinkdemo.mvp.model.Impl.LoginModelImpl;
import com.example.nanlinkdemo.mvp.presenter.LoginPresenter;
import com.example.nanlinkdemo.mvp.view.LoginView;
import com.example.nanlinkdemo.mvp.widget.MainActivity;
import com.example.nanlinkdemo.mvp.widget.RegisterActivity;
import com.example.nanlinkdemo.util.SnackBarUtil;


public class LoginPresenterImpl implements LoginPresenter {

    private final LoginView view;
    private final LoginModelImpl model;
    private boolean checked = false;
    private String email;
    private String password;


    public LoginPresenterImpl(LoginView loginView) {
        this.view = loginView;
        this.model = new LoginModelImpl(this);
    }

    @Override
    public void login(String email, String password) {
        model.login(email, password);
    }

    @Override
    public void sendMesToView(Message mes) {
        switch (mes.getCode()){
            case 200:
                view.gotoActivity(MainActivity.class);
                view.finish();
                view.saveEmail(mes.getData().getEmail());
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
                SnackBarUtil.show((Activity) view, "登录失败");
                break;
        }
    }

    @Override
    public void switchOnclick(View view) {
        switch (view.getId()){
            case R.id.btn_register:
                this.view.gotoActivity(RegisterActivity.class);
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
                    SnackBarUtil.show((Activity) this.view, "请输入邮箱");
                }else if (password.isEmpty()){
                    SnackBarUtil.show((Activity) this.view,"请输入密码");
                }else if (password.length() < 6 || password.length() > 20){
                    SnackBarUtil.show((Activity) this.view, "请输入6-20位密码");
                }else if (checked){
                    login(email, password);
                    this.view.starLoading();
                }else {
                    SnackBarUtil.show((Activity) this.view,"请勾选用户协议");
                }
                break;
        }
    }

    @Override
    public void stopLoading() {
        this.view.stopLoading();
    }

}
