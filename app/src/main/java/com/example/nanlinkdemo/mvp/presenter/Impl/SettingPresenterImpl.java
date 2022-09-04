package com.example.nanlinkdemo.mvp.presenter.Impl;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.mvp.model.Impl.SettingModelImpl;
import com.example.nanlinkdemo.mvp.presenter.SettingPresenter;
import com.example.nanlinkdemo.mvp.view.SettingView;
import com.example.nanlinkdemo.mvp.widget.SceneListFragment;
import com.example.nanlinkdemo.mvp.widget.SettingFragment;
import com.example.nanlinkdemo.mvp.widget.UserSettingFragment;

import java.util.ArrayList;

public class SettingPresenterImpl implements SettingPresenter {
    private final SettingModelImpl model;
    private final SettingView view;

    public SettingPresenterImpl(SettingView view) {
        this.view = view;
        model = new SettingModelImpl(this);
    }

    @Override
    public void getSettingListFromView() {
        model.getSettingList();
    }

    @Override
    public void showSettingListToView(ArrayList<Menu> settingArrayList) {
        view.showStories(settingArrayList);
    }

    @Override
    public void onClickSwitch(String settingText) {
        switch (settingText){
            case "账号设置":
                view.replaceFragment(SettingFragment.getInstance(), UserSettingFragment.getInstance());
                view.setToolbar(R.drawable.ic_back, settingText, 0, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view.replaceFragment(UserSettingFragment.getInstance(), SettingFragment.getInstance());
                        view.setToolbar(R.drawable.ic_back, "设置", 0, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                view.replaceFragment(SettingFragment.getInstance(), SceneListFragment.getInstance());
                                view.initToolbar();
                            }
                        }, null);
                    }
                }, null);
                break;
            case "语言设置":
                break;
            case "意见反馈":
                break;
            case "防止休眠":
                break;
        }
    }
}
