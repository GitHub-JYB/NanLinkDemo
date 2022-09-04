package com.example.nanlinkdemo.mvp.presenter;

import com.example.nanlinkdemo.bean.Menu;

import java.util.ArrayList;

public interface SceneListPresenter {


    void showMenuToView(ArrayList<Menu> menuArrayList);

    void getMenuFromView();
}
