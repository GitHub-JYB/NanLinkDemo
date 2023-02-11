package com.example.nanlinkdemo.mvp.model;

import com.example.nanlinkdemo.DB.bean.Fixture;
import com.example.nanlinkdemo.DB.bean.FixtureGroup;

public interface ManageFixtureModel {
    void getFixtureList();

    void updateFixture(Fixture fixture);

    void updateFixtureGroup(FixtureGroup fixtureGroup);
}
