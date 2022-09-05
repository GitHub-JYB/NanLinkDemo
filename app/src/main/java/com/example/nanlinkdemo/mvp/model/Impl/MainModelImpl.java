package com.example.nanlinkdemo.mvp.model.Impl;

import static com.example.nanlinkdemo.bean.Menu.TYPE_ITEM;
import static com.example.nanlinkdemo.bean.Menu.TYPE_LOGO;

import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.mvp.model.MainModel;
import com.example.nanlinkdemo.mvp.presenter.Impl.MainPresenterImpl;

import java.util.ArrayList;

public class MainModelImpl implements MainModel {

    private final MainPresenterImpl presenter;
    private ArrayList<Menu> menuArrayList;

    public MainModelImpl(MainPresenterImpl presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getMenu() {
        menuArrayList = new ArrayList<>();
        menuArrayList.add(new Menu(0,"", TYPE_LOGO));
        menuArrayList.add(new Menu(R.drawable.ic_user,"测试者", TYPE_ITEM));
        menuArrayList.add(new Menu());
        menuArrayList.add(new Menu(R.drawable.ic_add,"创建场景", TYPE_ITEM));
        menuArrayList.add(new Menu(R.drawable.ic_add,"创建场景群组", TYPE_ITEM));
        menuArrayList.add(new Menu(R.drawable.ic_sort,"排序", TYPE_ITEM));
        menuArrayList.add(new Menu());
        menuArrayList.add(new Menu(R.drawable.ic_set,"设置", TYPE_ITEM));
        menuArrayList.add(new Menu(R.drawable.ic_about,"有关", TYPE_ITEM));
        presenter.showMenuToView(menuArrayList);
    }


}
