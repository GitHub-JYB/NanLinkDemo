package com.example.NanLinkDemo.mvp.view;

import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.FixtureGroup;

import java.util.ArrayList;

public interface ControlModeView {
    void setPresenter();

    void showControls(ArrayList<FixtureGroup> fixtureGroups, ArrayList<Fixture> fixtures);
}
