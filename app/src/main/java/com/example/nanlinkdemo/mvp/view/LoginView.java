package com.example.nanlinkdemo.mvp.view;




public interface LoginView {

    void setPresenter();

    void setCheckImage(int resId);

    String getEmail();

    String getPassword();

    void finish();

    void showMistakeDialog(String title, String message, int type);

    void stopLoading();

    void startLoading();

    void updateEmail(String email);
}
