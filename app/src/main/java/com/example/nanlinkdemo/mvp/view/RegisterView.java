package com.example.nanlinkdemo.mvp.view;


import com.example.nanlinkdemo.ui.MyDialog;

public interface RegisterView {

    void setPresenter();

    void setCheckImage(int resId);

    String getEmail();

    String getPassword();

    void finish();

    String getConfirmPassword();

    String getNickName();

    String getCode();

    void stopLoading();

    void startLoading();

    void updateGetCodeBtn(boolean able);

    void updatedRegisterBtnBg(int res);

    void showMyDialog(int type, String title, String message, String neutralText, MyDialog.NeutralOnClickListener neutralListener);

    void dismissMyDialog();


    void updateGetCodeBtnText(String text);
}
