package com.example.nanlinkdemo.mvp.view;




public interface RegisterView {

    void setPresenter();

    void setCheckImage(int resId);

    void gotoActivity(Class<?> cls);

    String getEmail();

    String getPassword();

    void finish();

    String getConfirmPassword();

    String getNickName();

    String getCode();

}
