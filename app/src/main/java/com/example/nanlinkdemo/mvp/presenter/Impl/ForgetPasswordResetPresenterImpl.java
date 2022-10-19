package com.example.nanlinkdemo.mvp.presenter.Impl;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Message;
import com.example.nanlinkdemo.mvp.model.Impl.ForgetPasswordResetModelImpl;
import com.example.nanlinkdemo.mvp.view.ForgetPasswordResetView;
import com.example.nanlinkdemo.ui.MyDialog;
import com.example.nanlinkdemo.util.Constant;
import com.example.nanlinkdemo.util.SnackBarUtil;

public class ForgetPasswordResetPresenterImpl {
    private final ForgetPasswordResetView view;
    private final ForgetPasswordResetModelImpl model;
    private String password, confirmPassword;

    public ForgetPasswordResetPresenterImpl(ForgetPasswordResetView view) {
        this.view = view;
        model = new ForgetPasswordResetModelImpl(this);
    }

    public void switchOnclick(View v) {

        switch (v.getId()){
            case R.id.toolbar_left_btn:
                view.finish();
                break;
            case R.id.btn_resetPassword:
                password = view.getPassword();
                confirmPassword = view.getConfirmPassword();
                if (password.isEmpty()){
                    SnackBarUtil.show(v, "请输入密码");
                }else if (confirmPassword.isEmpty()) {
                    SnackBarUtil.show(v, "请输入确认密码");
                }else if (password.equals(confirmPassword)){
                    view.startLoading();
                    model.resetPassword(view.getEmail(), password, view.getCode());
                }else {
                    SnackBarUtil.show(v, "两次输入的密码不一致");
                }
        }

    }

    public void sendMesToView(Message message) {
        view.stopLoading();
        switch (message.getCode()) {
            case 200:
                view.showSuccessDialog("重置密码", "重置密码成功！\n您可使用新密码进行登录", 0, new MyDialog.NeutralOnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view.dismissDialog();
                        ARouter.getInstance().build(Constant.ACTIVITY_URL_Login).navigation();
                        view.finish();
                    }
                });
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
}
