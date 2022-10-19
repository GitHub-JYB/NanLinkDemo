package com.example.nanlinkdemo.mvp.view;




public interface ForgetPasswordView {

    void setPresenter();

    String getEmail();

    void finish();

    String getCode();

    void updateGetCodeBtn();

    void startLoading();

    void stopLoading();

    void showMistakeDialog(String title, String content, int type);

    void updateResetBtn(boolean able);
}
