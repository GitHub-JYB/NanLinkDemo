package com.example.NanLinkDemo.mvp.presenter;

import com.example.NanLinkDemo.bean.Menu;

import java.util.ArrayList;

public interface SceneListPresenter {


    void showMenuToView(ArrayList<Menu> menuArrayList);

    void getMenuFromView();
}
