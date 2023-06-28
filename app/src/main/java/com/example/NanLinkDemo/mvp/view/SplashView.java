package com.example.NanLinkDemo.mvp.view;

import com.example.NanLinkDemo.ui.MyDialog;

public interface SplashView {
    void finish();

    void showMyDialog(int type, String title, String message, String neutralText, MyDialog.NeutralOnClickListener neutralListener);

}
