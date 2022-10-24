package com.example.nanlinkdemo.mvp.view;

import com.example.nanlinkdemo.DB.bean.Scene;
import com.example.nanlinkdemo.DB.bean.SceneGroup;
import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.mvp.adapter.ThreePointAdapter;
import com.example.nanlinkdemo.ui.MyDialog;

import java.util.ArrayList;
import java.util.List;

public interface SceneGroupView {
    void updateRecycleView();

    void updateMenu();

    void showMenu(ArrayList<Menu> menuArrayList);

    void openDrawLayout();

    void closeDrawLayout();

    void showSceneList(List<Scene> sceneList);

    void showSnack(CharSequence message);

    void finish();

    void showMyDialog(int type, String title, String message, String neutralText, MyDialog.NeutralOnClickListener neutralOnClickListener);

    void showMyDialog(int type, String title, String message, String negativeText, MyDialog.NegativeOnClickListener negativeListener, String positiveText, MyDialog.PositiveOnClickListener positiveListener);


    void dismissSettingDialog();

    void dismissMyDialog();

    void showSettingDialog(ArrayList<String> settingList, ThreePointAdapter.OnClickListener listener);

    String getInputTextMyDialog();

    String getSceneGroupName();
}
