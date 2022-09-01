package com.example.nanlinkdemo.mvp.presenter;


import android.view.View;

import com.example.nanlinkdemo.bean.Menu;

import java.util.ArrayList;

public interface MainPresenter {


    void getMenuFromModel();

    void showMenuToView(ArrayList<Menu> menuArrayList);

    void menuSwitch(String menuText);

    void toolbarSwitch(View v);
}
