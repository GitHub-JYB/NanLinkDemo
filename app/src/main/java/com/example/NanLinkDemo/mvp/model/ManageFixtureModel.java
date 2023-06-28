package com.example.NanLinkDemo.mvp.model;

import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.FixtureGroup;

public interface ManageFixtureModel {
    void getFixtureList(String fixtureGroupName);

    void updateFixture(Fixture fixture);

    void updateFixtureGroup(FixtureGroup fixtureGroup);
}
