package com.example.NanLinkDemo.mvp.model;

import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.FixtureGroup;

public interface ControllerModeModel {
    void updateFixtureGroup(FixtureGroup fixtureGroup);

    void updateFixture(Fixture fixture);
}
