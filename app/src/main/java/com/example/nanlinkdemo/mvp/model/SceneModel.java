package com.example.nanlinkdemo.mvp.model;

import com.example.nanlinkdemo.DB.bean.Fixture;
import com.example.nanlinkdemo.DB.bean.FixtureGroup;
import com.example.nanlinkdemo.DB.bean.Scene;

public interface SceneModel {
    void getMenu();

    void getSortList();

    void getSettingList();

    void updateScene(Scene scene);

    void queryScene(String sceneName, int type);

    void deleteScene(Scene scene);

    void queryAllFixture(int type);

    void queryAllFixtureGroup(int type);

    void deleteFixture(Fixture fixture);

    void deleteFixtureGroup(FixtureGroup fixtureGroup);

    void queryFixtureFromFixtureGroupName(String fixtureGroupName, int type);

    void addFixtureGroup(String fixtureGroupName);

    void addFixture(String fixtureCH);

    void queryFixtureGroup(String fixtureGroupName, int type);

    void queryFixture(String fixtureCH, int type);

    void updateFixture(Fixture fixture);

    void updateFixtureGroup(FixtureGroup fixtureGroup);

    void getFixtureGroupMenu(int fixtureGroupPosition);

    void getFixtureMenu(int fixturePosition);
}
