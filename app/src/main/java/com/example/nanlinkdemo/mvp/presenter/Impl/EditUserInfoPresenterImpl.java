package com.example.nanlinkdemo.mvp.presenter.Impl;

import android.view.View;

import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.EditUser;
import com.example.nanlinkdemo.bean.Message;
import com.example.nanlinkdemo.mvp.model.Impl.EditUserInfoModelImpl;
import com.example.nanlinkdemo.mvp.presenter.EditUserInfoPresenter;
import com.example.nanlinkdemo.mvp.view.EditUserInfoView;
import com.example.nanlinkdemo.ui.MyDialog;

public class EditUserInfoPresenterImpl implements EditUserInfoPresenter {
    private final EditUserInfoView view;
    private final EditUserInfoModelImpl model;

    public EditUserInfoPresenterImpl(EditUserInfoView view) {
        this.view = view;
        this.model = new EditUserInfoModelImpl(this);
    }

    @Override
    public void switchOnclick(View v) {
        switch (v.getId()){
            case R.id.toolbar_left_btn:
                this.view.finish();
                break;
            case R.id.btn_edit_user:
                if (!MyApplication.getInstance().isOpenNetwork()){
                    view.showMyDialog(MyDialog.Read_OneBtn_WarningTitle_BlueOneBtn, "错误", "无法连接服务器", "重试", null);
                }else {
                    view.startLoading();
                    EditUser user = new EditUser();
                    user.setNickName(view.getNickName());
                    user.setVocation(view.getVocation());
                    model.editInfo(MyApplication.getOnlineUser().getToken(), user);
                }
                break;

        }

    }

    @Override
    public void sendMesToView(Message message) {
        view.stopLoading();
        switch (message.getCode()) {
            case 200:
                MyApplication.getOnlineUser().setNickName(view.getNickName());
                MyApplication.getOnlineUser().setVocation(view.getVocation());
                model.updateUser(MyApplication.getOnlineUser());
                view.showMyDialog(MyDialog.Read_OneBtn_NormalTitle_BlueOneBtn, "编辑账号信息", "修改已被保存", "完成", new MyDialog.NeutralOnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view.dismissMyDialog();
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
                view.showMyDialog(MyDialog.Read_OneBtn_WarningTitle_BlueOneBtn,"错误", message.getMsg().toString(),"重试", null);
                break;
        }
    }
}
