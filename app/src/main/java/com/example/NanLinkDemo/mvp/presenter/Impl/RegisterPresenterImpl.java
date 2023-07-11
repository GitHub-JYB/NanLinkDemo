package com.example.NanLinkDemo.mvp.presenter.Impl;

import android.content.Intent;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.bean.Message;
import com.example.NanLinkDemo.mvp.model.Impl.RegisterModelImpl;
import com.example.NanLinkDemo.mvp.presenter.RegisterPresenter;
import com.example.NanLinkDemo.mvp.view.RegisterView;
import com.example.NanLinkDemo.ui.MyDialog;
import com.example.NanLinkDemo.util.ApiClient;
import com.example.NanLinkDemo.util.Constant;
import com.example.NanLinkDemo.util.SnackBarUtil;


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
        switch (mes.getCode()){
            case 200:
                view.stopLoading();
                switch (function){
                    case ApiClient.Function_Register:
                        view.showMyDialog(MyDialog.Read_OneBtn_NormalTitle_BlueOneBtn,"注册", "注册成功！\n您可使用该邮箱进行登录", "完成", new MyDialog.NeutralOnClickListener() {
                            @Override
                            public void onClick(View v) {
                                view.dismissMyDialog();
                                ARouter.getInstance().build(Constant.ACTIVITY_URL_Login).navigation();
                                view.finish();
                            }
                        });
                        break;
                    case ApiClient.Function_GetCode:
                        view.updateGetCodeBtn(false);
                        model.startCountDown();
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
                showWarnToView();
                break;
        }
    }

    @Override
    public void showWarnToView(){
        view.stopLoading();
        view.showMyDialog(MyDialog.Read_OneBtn_WarningTitle_BlueOneBtn, "错误", "无法连接服务器", "重试", null);
    }

    @Override
    public void endCountDown() {
        view.updateGetCodeBtnText("获取验证码");
    }

    @Override
    public void sendProgressToView(Long aLong) {
        view.updateGetCodeBtnText("已发送 " + (60-aLong) + "s");
    }

    @Override
    public void switchOnclick(View view) {
        switch (view.getId()){

            case R.id.toolbar_left_btn:
                ARouter.getInstance().build(Constant.ACTIVITY_URL_Login).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).navigation();
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
                    this.view.startLoading();
                    if (!MyApplication.getInstance().isOpenNetwork()){
                        showWarnToView();
                        break;
                    }
                    model.register(email, password, code, nickName);
                }else {
                    SnackBarUtil.show(view, "请勾选用户协议");
                }
                break;

            case R.id.btn_get_code:
                email = this.view.getEmail();
                if (email.isEmpty()){
                    SnackBarUtil.show(view, "请输入邮箱");
                }else {
                    this.view.startLoading();
                    if (!MyApplication.getInstance().isOpenNetwork()){
                        showWarnToView();
                    }else {
                        model.getCode(email, Constant.Code_Register);
                    }
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
