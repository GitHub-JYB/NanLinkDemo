package com.example.nanlinkdemo.mvp.view;

import com.example.nanlinkdemo.ui.MyDialog;

public interface FeedbackView {
    void updateBtnSubmit(boolean able);

    String getFeedback();

    void finish();

    void startLoading();

    void stopLoading();

    void dismissMyDialog();

    void showMyDialog(int type, String title, String message, String neutralText, MyDialog.NeutralOnClickListener neutralOnClickListener);
}
