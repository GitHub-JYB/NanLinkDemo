package com.example.nanlinkdemo.mvp.model.Impl;

import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.DB.DataBase.MyDataBase;
import com.example.nanlinkdemo.DB.bean.Fixture;
import com.example.nanlinkdemo.DB.bean.FixtureGroup;
import com.example.nanlinkdemo.mvp.model.ManageFixtureModel;
import com.example.nanlinkdemo.mvp.presenter.Impl.ManageFixturePresenterImpl;

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
    public void getFixtureList() {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getFixtureDao()
                .getFixtureInfoFromFixtureGroup(MyApplication.getOnlineUser().getEmail(), MyApplication.getScene().getName(), "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Fixture>>() {
                    @Override
                    public void accept(List<Fixture> fixtures) throws Exception {
                        presenter.receiveFixtureList(fixtures);
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

                    }
                });
    }
}
