package com.example.NanLinkDemo.mvp.presenter.Impl;

import android.content.Intent;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.bean.Message;
import com.example.NanLinkDemo.mvp.model.Impl.ResetPasswordResetModelImpl;
import com.example.NanLinkDemo.mvp.presenter.ResetPasswordResetPresenter;
import com.example.NanLinkDemo.mvp.view.ResetPasswordResetView;
import com.example.NanLinkDemo.mvp.widget.ResetPasswordActivity;
import com.example.NanLinkDemo.ui.MyDialog;
import com.example.NanLinkDemo.util.Constant;
import com.example.NanLinkDemo.util.SnackBarUtil;

public class ResetPasswordResetPresenterImpl implements ResetPasswordResetPresenter {
    private final ResetPasswordResetView view;
    private final ResetPasswordResetModelImpl model;
    private String password, confirmPassword;

    public ResetPasswordResetPresenterImpl(ResetPasswordResetView view) {
        this.view = view;
        model = new ResetPasswordResetModelImpl(this);
    }

    @Override
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
                    if (MyApplication.getInstance().isOpenNetwork()){
                        model.resetPassword(view.getEmail(), password, view.getCode());
                    }else {
                        showWarnToView();
                    }
                }else {
                    SnackBarUtil.show(v, "两次输入的密码不一致");
                }
        }

    }

    @Override
    public void sendMesToView(Message message) {
        switch (message.getCode()) {
            case 200:
                view.stopLoading();
                if (view.getType() == ResetPasswordActivity.Type_Forget_Password){
                    view.showMyDialog(MyDialog.Read_OneBtn_NormalTitle_BlueOneBtn,"重置密码", "重置密码成功！\n您可使用新密码进行登录", "完成", new MyDialog.NeutralOnClickListener() {
                        @Override
                        public void onClick(View v) {
                            view.dismissMyDialog();
                            ARouter.getInstance().build(Constant.ACTIVITY_URL_Login).withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).navigation();
                        }
                    });
                }else if (view.getType() == ResetPasswordActivity.Type_Change_Password){
                    view.showMyDialog(MyDialog.Read_OneBtn_NormalTitle_BlueOneBtn,"修改密码", "修改密码成功！\n您可使用新密码进行登录", "完成", new MyDialog.NeutralOnClickListener() {
                        @Override
                        public void onClick(View v) {
                            view.dismissMyDialog();
                            ARouter.getInstance().build(Constant.ACTIVITY_URL_UserSetting).withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).navigation();
                        }
                    });
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
}
