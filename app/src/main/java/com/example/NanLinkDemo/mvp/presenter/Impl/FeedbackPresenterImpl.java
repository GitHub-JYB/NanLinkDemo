package com.example.NanLinkDemo.mvp.presenter.Impl;

import android.view.View;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.bean.Message;
import com.example.NanLinkDemo.mvp.model.Impl.FeedbackModelImpl;
import com.example.NanLinkDemo.mvp.presenter.FeedbackPresenter;
import com.example.NanLinkDemo.mvp.view.FeedbackView;
import com.example.NanLinkDemo.ui.MyDialog;

public class FeedbackPresenterImpl implements FeedbackPresenter {
    private final FeedbackView view;
    private final FeedbackModelImpl model;

    public FeedbackPresenterImpl(FeedbackView view) {
        this.view = view;
        this.model = new FeedbackModelImpl(this);
    }

    @Override
    public void switchOnclick(View v) {
        switch (v.getId()) {

            case R.id.toolbar_left_btn:
                view.finish();
                break;
            case R.id.btn_submit:
                view.startLoading();
                if (!MyApplication.getInstance().isOpenNetwork()){
                    showWarnToView();
                }else {
                    model.submitFeedback(view.getFeedback());
                }
                break;
        }
    }

    @Override
    public void checkText() {
       view.updateBtnSubmit(!view.getFeedback().isEmpty());
    }

    @Override
    public void sendMesToView(Message message) {
        switch (message.getCode()){
            case 200:
                view.stopLoading();
                view.showMyDialog(MyDialog.Read_OneBtn_NormalTitle_WhiteOneBtn,"意见反馈", "感谢您的意见反馈!", "完成", new MyDialog.NeutralOnClickListener() {
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
