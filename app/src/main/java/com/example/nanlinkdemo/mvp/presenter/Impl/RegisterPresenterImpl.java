package com.example.nanlinkdemo.mvp.presenter.Impl;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Message;
import com.example.nanlinkdemo.mvp.model.Impl.RegisterModelImpl;
import com.example.nanlinkdemo.mvp.presenter.RegisterPresenter;
import com.example.nanlinkdemo.mvp.view.RegisterView;
import com.example.nanlinkdemo.ui.MyDialog;
import com.example.nanlinkdemo.util.ApiClient;
import com.example.nanlinkdemo.util.Constant;
import com.example.nanlinkdemo.util.SnackBarUtil;


public class RegisterPresenterImpl implements RegisterPresenter {

    private final RegisterView view;
    private final RegisterModelImpl model;
    private boolean checked = false;
    private String email, password, confirmPassword, code, nickName;


    public RegisterPresenterImpl(RegisterView registerView) {
        this.view = registerView;
        this.model = new RegisterModelImpl(this);
    }

    @Override
    public void sendMesToView(Message mes, String function) {
        view.stopLoading();
        switch (mes.getCode()){
            case 200:
                switch (function){
                    case ApiClient.Function_Register:
                        view.showSuccessDialog("注册", "注册成功！\n您可使用该邮箱进行登录", 0, new MyDialog.NeutralOnClickListener() {
                            @Override
                            public void onClick(View v) {
                                view.dismissDialog();
                                ARouter.getInstance().build(Constant.ACTIVITY_URL_Login).navigation();
                                view.finish();
                            }
                        });
                        break;
                    case ApiClient.Function_GetCode:
                        view.updateGetCodeBtn();
                        break;
                }
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
                view.showMistakeDialog("错误", mes.getMsg().toString(),0);
                break;
        }
    }

    @Override
    public void switchOnclick(View view) {
        switch (view.getId()){

            case R.id.toolbar_left_btn:
                this.view.finish();
                break;
            case R.id.login_check:
                if (checked){
                    this.view.setCheckImage(R.drawable.unchecked);
                    checked = false;
                }else {
                    this.view.setCheckImage(R.drawable.checked);
                    checked = true;
                }
                checkRegisterMessage();
                break;
            case R.id.btn_register:
                email = this.view.getEmail();
                password = this.view.getPassword();
                confirmPassword = this.view.getConfirmPassword();
                code = this.view.getCode();
                nickName = this.view.getNickName();


                if (email.isEmpty()){
                    SnackBarUtil.show(view, "请输入邮箱");
                }else if (password.isEmpty()){
                    SnackBarUtil.show(view, "请输入密码");

                }else if (password.length() < 6 || password.length() > 20){
                    SnackBarUtil.show(view, "请输入6-20位密码");

                }else if (checked){
                    if (!MyApplication.getInstance().isOpenNetwork()){
                        this.view.showMistakeDialog("错误", "无法连接服务器",0);
                        break;
                    }
                    this.view.startLoading();
                    model.register(email, password, code, nickName);
                }else {
                    SnackBarUtil.show(view, "请勾选用户协议");
                }
                break;

            case R.id.btn_get_code:
                email = this.view.getEmail();
                if (email.isEmpty()){
                    SnackBarUtil.show(view, "请输入邮箱");
                }else if (!MyApplication.getInstance().isOpenNetwork()){
                    this.view.showMistakeDialog("错误", "无法连接服务器",0);
                }else {
                    this.view.startLoading();
                    model.getCode(email, Constant.Code_Register);
                }
                break;
        }
    }


    @Override
    public void checkRegisterMessage() {
        email = view.getEmail();
        password = view.getPassword();
        confirmPassword = view.getConfirmPassword();
        code = view.getCode();
        nickName = view.getNickName();
        if (!email.isEmpty() & !password.isEmpty() & !confirmPassword.isEmpty() & !code.isEmpty() & !nickName.isEmpty() & checked){
            view.updatedRegisterBtnBg(R.drawable.bg_able_btn_login);
        }else {
            view.updatedRegisterBtnBg(R.drawable.bg_unable_btn_login);

        }
    }

}
