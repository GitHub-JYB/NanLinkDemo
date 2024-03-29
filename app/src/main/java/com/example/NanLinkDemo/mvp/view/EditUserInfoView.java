package com.example.NanLinkDemo.mvp.view;

import com.example.NanLinkDemo.ui.MyDialog;

public interface EditUserInfoView {
    void setPresenter();

    void setEmail(String email);

    void setVocation(String vocation);

    void setNickName(String nickName);

    String getVocation();

    String getNickName();

    void finish();

    void startLoading();

    void stopLoading();

    void showMyDialog(int type, String title, String message, String neutralText, MyDialog.NeutralOnClickListener neutralListener);

    void dismissMyDialog();
    }
