package com.example.NanLinkDemo.mvp.view;

import com.example.NanLinkDemo.bean.Menu;

import java.util.ArrayList;

public interface SceneListView {

    void setPresenter();



    void showStories(ArrayList<Menu> menuList);
}
