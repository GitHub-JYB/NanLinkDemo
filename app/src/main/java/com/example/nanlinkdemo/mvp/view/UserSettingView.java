package com.example.nanlinkdemo.mvp.view;

import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.bean.RegisterUser;
import com.example.nanlinkdemo.ui.MyDialog;

import java.util.ArrayList;

public interface UserSettingView {
    void updateRecyclerView();

    void setPresenter();

    void showStories(ArrayList<Menu> settingList, ArrayList<RegisterUser> userList);

    void finish();

    void showMyDialog(int type, String title, String message, String neutralText, MyDialog.NeutralOnClickListener neutralListener);

    void showMyDialog(int type, String title, String message, String negativeText, MyDialog.NegativeOnClickListener negativeListener, String positiveText, MyDialog.PositiveOnClickListener positiveListener);

    }
