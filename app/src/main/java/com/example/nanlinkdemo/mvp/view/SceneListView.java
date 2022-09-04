package com.example.nanlinkdemo.mvp.view;

import com.example.nanlinkdemo.bean.Menu;

import java.util.ArrayList;

public interface SceneListView {

    void setPresenter();



    void showStories(ArrayList<Menu> menuList);
}
