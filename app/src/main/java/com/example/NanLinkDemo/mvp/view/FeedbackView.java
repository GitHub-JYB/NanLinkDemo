package com.example.NanLinkDemo.mvp.view;

import com.example.NanLinkDemo.ui.MyDialog;

public interface FeedbackView {
    void updateBtnSubmit(boolean able);

    String getFeedback();

    void finish();

    void startLoading();

    void stopLoading();

    void dismissMyDialog();

    void showMyDialog(int type, String title, String message, String neutralText, MyDialog.NeutralOnClickListener neutralOnClickListener);
}
