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

    private final LoginView loginView;
    private final LoginModelImpl loginModelImpl;
    private boolean checked = false;
    private String email;
    private String password;


    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.loginModelImpl = new LoginModelImpl(this);
    }

    @Override
    public void login(String email, String password) {
        loginModelImpl.login(email, password);
    }

    @Override
    public void sendMesToView(Message mes) {
        switch (mes.getCode()){
            case 200:
                loginView.gotoActivity(MainActivity.class);
                loginView.finish();
                loginView.saveEmail(mes.getData().getEmail());
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
                SnackBarUtil.show((Activity) loginView, "登录失败");
                break;
        }
    }

    @Override
    public void switchOnclick(View view) {
        switch (view.getId()){
            case R.id.btn_register:
                loginView.gotoActivity(RegisterActivity.class);
                break;
            case R.id.login_check:
                if (checked){
                    loginView.setCheckImage(R.drawable.unchecked);
                    checked = false;
                }else {
                    loginView.setCheckImage(R.drawable.checked);
                    checked = true;
                }
                break;
            case R.id.btn_login:
                email = loginView.getEmail();
                password = loginView.getPassword();
                if (email.isEmpty()){
                    SnackBarUtil.show((Activity) loginView, "请输入邮箱");
                }else if (password.isEmpty()){
                    SnackBarUtil.show((Activity) loginView,"请输入密码");
                }else if (password.length() < 6 || password.length() > 20){
                    SnackBarUtil.show((Activity) loginView, "请输入6-20位密码");
                }else if (checked){
                    login(email, password);
                    loginView.starLoading();
                }else {
                    SnackBarUtil.show((Activity) loginView,"请勾选用户协议");
                }
                break;
        }
    }

    @Override
    public void stopLoading() {
        loginView.stopLoading();
    }

}
