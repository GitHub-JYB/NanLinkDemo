package com.example.nanlinkdemo.mvp.view;




public interface LoginView {

    void setPresenter();

    void setCheckImage(int resId);

    void gotoActivity(Class<?> cls);

    String getEmail();

    String getPassword();

    void finish();

    void saveEmail(String email);

    void starLoading();

    void stopLoading();
}
