package com.example.nanlinkdemo.mvp.view;


import android.os.Bundle;

import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.ui.MyDialog;

import java.util.ArrayList;

public interface SettingView {
    void setPresenter();

    void showStories(ArrayList<Menu> settingList);

    void showMyDialog(int type, String title, String message, String neutralText, MyDialog.NeutralOnClickListener neutralListener);

    void finish();

}
