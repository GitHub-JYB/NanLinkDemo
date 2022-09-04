package com.example.nanlinkdemo.mvp.view;

import android.view.View;

import androidx.fragment.app.Fragment;

public interface AboutView {
    void setPresenter();

    void replaceFragment(Fragment oldFragment, Fragment newFragment);

    void setToolbar(int leftBtnResId, String title, int rightBtnResId, View.OnClickListener leftListener, View.OnClickListener rightListener);

    void initToolbar();
}
