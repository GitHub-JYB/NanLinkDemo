package com.example.NanLinkDemo.mvp.view;

import com.example.NanLinkDemo.DB.bean.Scene;
import com.example.NanLinkDemo.bean.Menu;
import com.example.NanLinkDemo.mvp.adapter.ThreePointAdapter;
import com.example.NanLinkDemo.ui.MyDialog;

import java.util.ArrayList;
import java.util.List;

public interface SceneGroupView {
    void updateRecycleView();

    void setTitle(String title);

    void initMenu();

    void showMenu(ArrayList<Menu> menuArrayList);

    void openDrawLayout();

    void closeDrawLayout();

    void showSceneList(List<Scene> sceneList);

    void finish();

    void showMyDialog(int type, String title, String bigSizeMessage, String smallSizeMessage, String negativeText, MyDialog.NegativeOnClickListener negativeListener, String positiveText, MyDialog.PositiveOnClickListener positiveListener);

    void showMyDialog(int type, String title, String bigSizeMessageOne, String smallSizeMessageOne, MyDialog.MessageOneOnClickListener messageOneListener, String bigSizeMessageTwo, String smallSizeMessageTwo, MyDialog.MessageTwoOnClickListener messageTwoListener, String neutralText, MyDialog.NeutralOnClickListener neutralListener);

    void showMyDialog(int type, String title, String message, String neutralText, MyDialog.NeutralOnClickListener neutralListener);

    void showMyDialog(int type, String title, String message, String negativeText, MyDialog.NegativeOnClickListener negativeListener, String positiveText, MyDialog.PositiveOnClickListener positiveListener);

    void dismissSettingDialog();

    void dismissMyDialog();

    void showSettingDialog(ArrayList<String> settingList, ThreePointAdapter.OnClickListener listener);

    String getInputTextMyDialog();

    void showSortList(ArrayList<Menu> sortArrayList);

    void showSettingList(ArrayList<Menu> settingArrayList);
}
