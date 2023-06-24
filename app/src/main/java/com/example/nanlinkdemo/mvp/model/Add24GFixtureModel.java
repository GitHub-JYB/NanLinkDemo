package com.example.nanlinkdemo.mvp.model;



import com.example.nanlinkdemo.DB.bean.Fixture;
import com.example.nanlinkdemo.DB.bean.Scene;

import java.util.ArrayList;

public interface Add24GFixtureModel {
    void getListData();

    void getBoxViewData();

    void addFixture(Fixture fixture);

    void updateScene(Scene scene);
}
