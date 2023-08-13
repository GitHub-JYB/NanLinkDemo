package com.example.NanLinkDemo.mvp.model;

import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.FixtureGroup;

public interface CameraModel {
    void getFixture(int id);

    void getFixtureGroup(int id);

    void updateFixture(Fixture fixture);

    void updateFixtureGroup(FixtureGroup fixtureGroup);
}
