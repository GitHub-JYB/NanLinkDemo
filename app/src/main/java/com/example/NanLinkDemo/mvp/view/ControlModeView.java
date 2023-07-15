package com.example.NanLinkDemo.mvp.view;

import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.FixtureGroup;
import com.example.NanLinkDemo.ui.MyDialog;

import java.util.ArrayList;

public interface ControlModeView {
    void setPresenter();

    void showControls(ArrayList<FixtureGroup> hasFixtureGroupList, ArrayList<Fixture> hasGroupFixtureList, ArrayList<Fixture> noGroupFixtureList);

    void showMyDialog(int type, String title, String bigSizeMessage, String smallSizeMessage, String negativeText, MyDialog.NegativeOnClickListener negativeListener, String positiveText, MyDialog.PositiveOnClickListener positiveListener);

    String getInputTextMyDialog();

    void clearInputText();

    void dismissMyDialog();


}
