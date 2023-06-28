package com.example.NanLinkDemo.mvp.model;



import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.Scene;

public interface Add24GFixtureModel {
    void getListData();

    void getBoxViewData();

    void addFixture(Fixture fixture);

    void updateScene(Scene scene);
}
