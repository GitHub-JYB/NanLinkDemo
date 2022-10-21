package com.example.nanlinkdemo.mvp.view;

import com.example.nanlinkdemo.DB.bean.Scene;
import com.example.nanlinkdemo.DB.bean.SceneGroup;
import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.ui.MyDialog;

import java.util.ArrayList;
import java.util.List;

public interface MainView {

    void showMenu(ArrayList<Menu> menuArrayList);

    void openDrawLayout();

    void closeDrawLayout();

    void showMyDialog(int type, String title, String message, String neutralText, MyDialog.NeutralOnClickListener neutralListener);

    void showMyDialog(int type, String title, String message, String negativeText, MyDialog.NegativeOnClickListener negativeListener, String positiveText, MyDialog.PositiveOnClickListener positiveListener);

    void showSceneList(List<Scene> sceneList, List<SceneGroup> sceneGroupList);

    void updateSceneList();

    void showThreePointMenu(ArrayList<String> threePointList, int type, int position);

    void dismissSettingDialog();

    String getInputTextMyDialog();

    void dismissMyDialog();

    void deleteScene(int furtherPosition);

    void deleteSceneGroup(int furtherPosition);

    void showSnack(CharSequence message);
}
