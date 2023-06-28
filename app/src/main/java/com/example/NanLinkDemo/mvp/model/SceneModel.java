package com.example.NanLinkDemo.mvp.model;

import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.FixtureGroup;
import com.example.NanLinkDemo.DB.bean.Scene;

public interface SceneModel {
    void getMenu();

    void getSortList();

    void getSettingList();

    void updateScene(Scene scene);

    void deleteScene(Scene scene);

    void deleteFixture(Fixture fixture);

    void deleteFixtureGroup(FixtureGroup fixtureGroup);

    void addFixtureGroup(FixtureGroup fixtureGroup);

    void addFixture(Fixture fixture);

    void updateFixture(Fixture fixture);

    void updateFixtureGroup(FixtureGroup fixtureGroup);

    void getFixtureGroupMenu(int fixtureGroupPosition);

    void getFixtureMenu(int fixturePosition);

    void getFixtureMenu(Fixture fixture);

    void queryFixtureGroupList();

    void queryFixtureList();

    void queryScene(String sceneName);
}
