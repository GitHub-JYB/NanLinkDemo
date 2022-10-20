package com.example.nanlinkdemo.mvp.presenter.Impl;



import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Message;
import com.example.nanlinkdemo.mvp.model.Impl.ForgetPasswordModelImpl;
import com.example.nanlinkdemo.mvp.presenter.ForgetPasswordPresenter;
import com.example.nanlinkdemo.mvp.view.ForgetPasswordView;
import com.example.nanlinkdemo.util.ApiClient;
import com.example.nanlinkdemo.util.Constant;
import com.example.nanlinkdemo.util.SnackBarUtil;

public class ForgetPasswordPresenterImpl implements ForgetPasswordPresenter {
    private final ForgetPasswordView view;
    private final ForgetPasswordModelImpl model;
    private String email, code;

    public ForgetPasswordPresenterImpl(ForgetPasswordView view) {
        this.view = view;
        model = new ForgetPasswordModelImpl(this);
    }

    @Override
    public void switchOnclick(View v) {
        switch (v.getId()){
            case R.id.toolbar_left_btn:
                this.view.finish();
                break;
            case R.id.btn_get_code:
                email = view.getEmail();
                if (email.isEmpty()){
                    SnackBarUtil.show(v, "请输入邮箱");
                }else if (!MyApplication.getInstance().isOpenNetwork()){
                    view.showMistakeDialog("错误", "无法连接服务器",0);
                }else {
                    view.startLoading();
                    model.getCode(email, Constant.Code_ResetPassword);
                }
                break;
            case R.id.btn_resetPassword:
                email = view.getEmail();
                code = view.getCode();
                if (email.isEmpty()){
                    SnackBarUtil.show(v, "请输入邮箱");
                }else if (code.isEmpty()){
                    SnackBarUtil.show(v, "请输入验证码");
                }else if (!MyApplication.getInstance().isOpenNetwork()){
                    view.showMistakeDialog("错误", "无法连接服务器",0);
                }else {
                    view.startLoading();
                    model.verifyCode(email, code);
                }
                break;

        }
    }

    @Override
    public void sendMesToView(Message message, String function) {
        view.stopLoading();
        switch (message.getCode()) {
            case 200:
                switch (function){
                    case ApiClient.Function_GetCode:
                        view.updateResetBtn(false);
                        model.startCountDown();
                        break;
                    case ApiClient.Function_VerifyCode:
                        ARouter.getInstance().build(Constant.ACTIVITY_URL_ForgetPasswordReset).withString("email", email).withString("code", code).navigation();
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
                view.showMistakeDialog("错误", message.getMsg().toString(),0);
                break;
        }
    }

    @Override
    public void updateProgressCountToView(Long aLong) {
        view.updateGetCodeBtnText("已发送 " + (60-aLong) + "s");
    }

    @Override
    public void endCountToView() {
        view.updateResetBtn(true);
        view.updateGetCodeBtnText("获取验证码");

    }

    @Override
    public void checkCode() {
        if (view.getCode().length() >= 4 & !view.getEmail().isEmpty()){
            view.updateResetBtn(true);
        }else {
            view.updateResetBtn(false);
        }
    }

}
