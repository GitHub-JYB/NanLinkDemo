package com.example.nanlinkdemo.mvp.view;

import com.example.nanlinkdemo.ui.MyDialog;

public interface SplashView {
    void finish();

    void showMyDialog(int type, String title, String message, String neutralText, MyDialog.NeutralOnClickListener neutralListener);

}
