package com.example.nanlinkdemo.mvp.presenter.Impl;

import android.app.Activity;
import android.view.View;

import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Message;
import com.example.nanlinkdemo.mvp.model.Impl.RegisterModelImpl;
import com.example.nanlinkdemo.mvp.presenter.RegisterPresenter;
import com.example.nanlinkdemo.mvp.view.RegisterView;
import com.example.nanlinkdemo.mvp.widget.MainActivity;
import com.example.nanlinkdemo.util.SnackBarUtil;


public class RegisterPresenterImpl implements RegisterPresenter {

    private final RegisterView registerView;
    private final RegisterModelImpl registerModelImpl;
    private boolean checked = false;


    public RegisterPresenterImpl(RegisterView registerView) {
        this.registerView = registerView;
        this.registerModelImpl = new RegisterModelImpl(this);
    }

    @Override
    public void register(String email, String password, String code, String nickName) {
        registerModelImpl.register(email, password, code, nickName);
    }

    @Override
    public void sendMesToView(Message mes) {
        switch (mes.getCode()){
            case 200:
                registerView.gotoActivity(MainActivity.class);
                registerView.finish();
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
                SnackBarUtil.show((Activity) registerView, "注册失败");

                break;
        }
    }

    @Override
    public void switchOnclick(View view) {
        switch (view.getId()){

            case R.id.check:
                if (checked){
                    registerView.setCheckImage(R.drawable.unchecked);
                    checked = false;
                }else {
                    registerView.setCheckImage(R.drawable.checked);
                    checked = true;
                }
                break;
            case R.id.btn_register:
                String email = registerView.getEmail();
                String password = registerView.getPassword();
                String confirmPassword = registerView.getConfirmPassword();
                String code = registerView.getCode();
                String nickName = registerView.getNickName();


                if (email.isEmpty()){
                    SnackBarUtil.show((Activity) registerView, "请输入邮箱");
                }else if (password.isEmpty()){
                    SnackBarUtil.show((Activity) registerView, "请输入密码");

                }else if (password.length() < 6 || password.length() > 20){
                    SnackBarUtil.show((Activity) registerView, "请输入6-20位密码");

                }else if (checked){
                    register(email, password, code, nickName);
                }else {
                    SnackBarUtil.show((Activity) registerView, "请勾选用户协议");
                }
                break;
        }
    }

}
