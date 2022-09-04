package com.example.nanlinkdemo.mvp.view;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.nanlinkdemo.bean.Menu;

import java.util.ArrayList;

public interface MainView {


    void initToolbar();

    void showMenu(ArrayList<Menu> menuArrayList);

    void openDrawLayout();

    void closeDrawLayout();

    void showMenuDialog(String title, String message, int type);

    void replaceFragment(Fragment oldFragment, Fragment newFragment);

    void setToolbar(int leftBtnResId, String title, int rightBtnResId, View.OnClickListener leftListener, View.OnClickListener rightListener);
}
