package com.example.nanlinkdemo.mvp.view;




public interface RegisterView {

    void setPresenter();

    void setCheckImage(int resId);

    String getEmail();

    String getPassword();

    void finish();

    String getConfirmPassword();

    String getNickName();

    String getCode();

}
