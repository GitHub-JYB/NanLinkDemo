package com.example.nanlinkdemo.mvp.model.Impl;


import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.DB.DataBase.MyDataBase;
import com.example.nanlinkdemo.DB.bean.User;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.Menu;
import com.example.nanlinkdemo.bean.RegisterUser;
import com.example.nanlinkdemo.mvp.model.UserSettingModel;
import com.example.nanlinkdemo.mvp.presenter.Impl.UserSettingPresenterImpl;

import java.util.ArrayList;
import java.util.List;

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
    public void getSettingList(User user) {
        settingArrayList = new ArrayList<>();
        settingArrayList.add(new Menu("编辑账号信息", R.drawable.ic_setting_expand));
        settingArrayList.add(new Menu("修改密码", R.drawable.ic_setting_expand));
        settingArrayList.add(new Menu("退出登录", R.drawable.ic_setting_expand));

        userArrayList = new ArrayList<>();
        userArrayList.add(new RegisterUser(user.getNickName(), user.getEmail()));
        presenter.showSettingListToView(settingArrayList, userArrayList);
    }

    @Override
    public void getLastUser() {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getUserDao()
                .getUserFromTypeInfo("lastUser")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> users) throws Exception {
                        presenter.receiveLastUser(users);
                    }
                });
    }

    @Override
    public void getOnlineUser() {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getUserDao()
                .getUserFromTypeInfo("online")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> users) throws Exception {
                        presenter.receiveOnlineUser(users);
                    }
                });
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
                        presenter.updateOnlineUser();
                    }
                });
    }
}
