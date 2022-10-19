package com.example.nanlinkdemo.mvp.view;




public interface LoginView {

    void setPresenter();

    void setCheckImage(int resId);

    String getEmail();

    String getPassword();

    void finish();

    void saveEmail(String email);

    void showMistakeDialog(String title, String message, int type);

    void saveLogin();

    void stopLoading();

    void startLoading();
}
