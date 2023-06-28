package com.example.NanLinkDemo.mvp.view;


import com.example.NanLinkDemo.bean.Menu;
import com.example.NanLinkDemo.ui.MyDialog;

import java.util.ArrayList;

public interface SettingView {
    void setPresenter();

    void showStories(ArrayList<Menu> settingList);

    void showMyDialog(int type, String title, String message, String neutralText, MyDialog.NeutralOnClickListener neutralListener);

    void finish();

}
