package com.example.NanLinkDemo.mvp.model.Impl;


import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.DataBase.MyDataBase;
import com.example.NanLinkDemo.DB.bean.User;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.bean.Menu;
import com.example.NanLinkDemo.mvp.model.SettingModel;
import com.example.NanLinkDemo.mvp.presenter.Impl.SettingPresenterImpl;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SettingModelImpl implements SettingModel {

    private final SettingPresenterImpl presenter;
    private ArrayList<Menu> settingArrayList;

    public SettingModelImpl(SettingPresenterImpl presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getSettingList() {
        settingArrayList = new ArrayList<>();
        settingArrayList.add(new Menu("账号设置", R.drawable.ic_setting_expand));
        settingArrayList.add(new Menu("语言设置", R.drawable.ic_setting_expand));
        settingArrayList.add(new Menu("意见反馈", R.drawable.ic_setting_expand));
        if (MyApplication.getOnlineUser().isKeepScreenOn()){
            settingArrayList.add(new Menu("防止休眠", R.drawable.ic_setting_switch_open));
        }else {
            settingArrayList.add(new Menu("防止休眠", R.drawable.ic_setting_switch_close));
        }

        presenter.showSettingListToView(settingArrayList);
    }

    @Override
    public void updateUser(User user) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getUserDao()
                .updateInfo(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                    }
                });
    }
}
