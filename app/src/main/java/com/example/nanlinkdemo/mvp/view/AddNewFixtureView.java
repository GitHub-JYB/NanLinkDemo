package com.example.nanlinkdemo.mvp.view;

import com.example.nanlinkdemo.bean.AddFixtureType;
import com.example.nanlinkdemo.bean.Menu;

import java.util.ArrayList;

public interface AddNewFixtureView {

    void setPresenter();


    void finish();

    void showAddType(ArrayList<AddFixtureType> arrayList);
}
