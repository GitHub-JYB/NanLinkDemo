package com.example.nanlinkdemo.mvp.view;

import com.example.nanlinkdemo.bean.Menu;

import java.util.ArrayList;

public interface MainView {

    void showMenu(ArrayList<Menu> menuArrayList);

    void openDrawLayout();

    void closeDrawLayout();

    void showMenuDialog(String title, String message, int type);

}
