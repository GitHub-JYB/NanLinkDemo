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

    void showMistakeDialog(String title, String content, int type);

    void dismissDialog();

    void showSuccessDialog(String title, String content, int type, MyDialog.NeutralOnClickListener listener);

    void updateGetCodeBtnText(String text);
}
