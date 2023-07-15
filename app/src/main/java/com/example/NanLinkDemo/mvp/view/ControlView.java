package com.example.NanLinkDemo.mvp.view;

import com.example.NanLinkDemo.bean.Menu;

import java.util.ArrayList;

public interface ControlView {
    void setTitle(String title);

    void initMenu();

    void showMenu(ArrayList<Menu> menuArrayList);

    void openDrawLayout();

    void closeDrawLayout();
}
