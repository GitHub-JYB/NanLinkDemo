package com.example.NanLinkDemo.mvp.model.Impl;


import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.DataBase.MyDataBase;
import com.example.NanLinkDemo.DB.bean.User;
import com.example.NanLinkDemo.R;
import com.example.NanLinkDemo.bean.Menu;
import com.example.NanLinkDemo.bean.RegisterUser;
import com.example.NanLinkDemo.mvp.model.UserSettingModel;
import com.example.NanLinkDemo.mvp.presenter.Impl.UserSettingPresenterImpl;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class UserSettingModelImpl implements UserSettingModel {

    private final UserSettingPresenterImpl presenter;
    private ArrayList<Menu> settingArrayList;
    private ArrayList<RegisterUser> userArrayList;

    public UserSettingModelImpl(UserSettingPresenterImpl presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getSettingList() {
        settingArrayList = new ArrayList<>();

        if (MyApplication.getOnlineUser().getEmail().equals("Guest")){
            settingArrayList.add(new Menu("注册", R.drawable.ic_setting_expand));
            settingArrayList.add(new Menu("登录", R.drawable.ic_setting_expand));
        }else {
            settingArrayList.add(new Menu("编辑账号信息", R.drawable.ic_setting_expand));
            settingArrayList.add(new Menu("修改密码", R.drawable.ic_setting_expand));
            settingArrayList.add(new Menu("退出登录", R.drawable.ic_setting_expand));
        }
        userArrayList = new ArrayList<>();
        userArrayList.add(new RegisterUser(MyApplication.getOnlineUser().getNickName(), MyApplication.getOnlineUser().getEmail()));
        presenter.showSettingListToView(settingArrayList, userArrayList);
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
