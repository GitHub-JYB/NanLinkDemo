package com.example.nanlinkdemo.mvp.view;

import com.example.nanlinkdemo.DB.bean.Fixture;
import com.example.nanlinkdemo.DB.bean.FixtureGroup;
import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.mvp.adapter.ThreePointAdapter;
import com.example.nanlinkdemo.ui.MyDialog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface SceneView {
    void updateRecycleView();

    void setTitle(String title);

    void initMenu();

    void showMenu(ArrayList<Menu> menuArrayList);

    void openDrawLayout();

    void closeDrawLayout();

    void showFixtureList(List<FixtureGroup> fixtureGroupList, List<Fixture> fixtureList);

    void showSortList(ArrayList<Menu> sortArrayList);

    void showSettingList(ArrayList<Menu> settingArrayList);

    void showSettingDialog(ArrayList<String> settingList, ThreePointAdapter.OnClickListener listener);

    void showMyDialog(int type, String title, String bigSizeMessage, String smallSizeMessage, String negativeText, MyDialog.NegativeOnClickListener negativeListener, String positiveText, MyDialog.PositiveOnClickListener positiveListener);

    void showMyDialog(int type, String title, String bigSizeMessageOne, String smallSizeMessageOne, MyDialog.MessageOneOnClickListener messageOneListener, String bigSizeMessageTwo, String smallSizeMessageTwo, MyDialog.MessageTwoOnClickListener messageTwoListener, String neutralText, MyDialog.NeutralOnClickListener neutralListener);

    void showMyDialog(int type, String title, String message, String neutralText, MyDialog.NeutralOnClickListener neutralListener);

    void showMyDialog(int type, String title, String message, String negativeText, MyDialog.NegativeOnClickListener negativeListener, String positiveText, MyDialog.PositiveOnClickListener positiveListener);

    void dismissMyDialog();

    void finish();

    String getInputTextMyDialog();

    void dismissSettingDialog();
}
