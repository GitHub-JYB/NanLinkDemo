package com.example.nanlinkdemo.mvp.view;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.mvp.widget.SettingFragment;
import com.example.nanlinkdemo.mvp.widget.UserSettingFragment;

import java.util.ArrayList;

public interface SettingView {
    void setPresenter();

    void showStories(ArrayList<Menu> settingList);

    void replaceFragment(Fragment oldFragment, Fragment newFragment);

    void setToolbar(int leftBtnResId, String title, int rightBtnResId, View.OnClickListener leftListener, View.OnClickListener rightListener);

    void initToolbar();
}
