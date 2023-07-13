package com.example.NanLinkDemo.mvp.model.Impl;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.DataBase.MyDataBase;
import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.FixtureGroup;
import com.example.NanLinkDemo.mvp.model.ManageFixtureModel;
import com.example.NanLinkDemo.mvp.presenter.Impl.ManageFixturePresenterImpl;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ManageFixtureModelImpl implements ManageFixtureModel {
    private final ManageFixturePresenterImpl presenter;

    public ManageFixtureModelImpl(ManageFixturePresenterImpl presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getFixtureList(String fixtureGroupName) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getFixtureDao()
                .getFixtureInfoFromFixtureGroup(MyApplication.getOnlineUser().getEmail(), MyApplication.getScene().getName(), fixtureGroupName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Fixture>>() {
                    @Override
                    public void accept(List<Fixture> fixtures) throws Exception {
                        presenter.receiveFixtureList(fixtureGroupName, fixtures);
                    }
                });
    }

    @Override
    public void updateFixture(Fixture fixture) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getFixtureDao()
                .updateFixtureInfo(fixture)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        presenter.completeUpdateFixture();
                    }
                });
    }

    @Override
    public void updateFixtureGroup(FixtureGroup fixtureGroup) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getFixtureGroupDao()
                .updateFixtureGroupInfo(fixtureGroup)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        presenter.completeUpdateFixtureGroup();
                    }
                });
    }
}
