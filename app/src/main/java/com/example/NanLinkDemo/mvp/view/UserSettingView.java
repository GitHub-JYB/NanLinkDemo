package com.example.NanLinkDemo.mvp.view;

import com.example.NanLinkDemo.bean.Menu;
import com.example.NanLinkDemo.bean.RegisterUser;
import com.example.NanLinkDemo.ui.MyDialog;

import java.util.ArrayList;

public interface UserSettingView {
    void updateRecyclerView();

    void setPresenter();

    void showStories(ArrayList<Menu> settingList, ArrayList<RegisterUser> userList);

    void finish();

    void showMyDialog(int type, String title, String message, String neutralText, MyDialog.NeutralOnClickListener neutralListener);

    void showMyDialog(int type, String title, String message, String negativeText, MyDialog.NegativeOnClickListener negativeListener, String positiveText, MyDialog.PositiveOnClickListener positiveListener);

    }
