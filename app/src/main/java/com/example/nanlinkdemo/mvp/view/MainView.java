package com.example.nanlinkdemo.mvp.view;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nanlinkdemo.bean.Menu;

import java.util.ArrayList;

public interface MainView {


    void showMenu(ArrayList<Menu> menuArrayList);

    void openDrawLayout();

    void showMenuDialog(String menuText);
}
