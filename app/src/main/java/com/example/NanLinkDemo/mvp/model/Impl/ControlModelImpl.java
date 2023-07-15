package com.example.NanLinkDemo.mvp.model.Impl;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.DataBase.MyDataBase;
import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.FixtureGroup;
import com.example.NanLinkDemo.mvp.model.ControlModel;
import com.example.NanLinkDemo.mvp.presenter.Impl.ControlPresenterImpl;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ControlModelImpl implements ControlModel {
    private final ControlPresenterImpl presenter;

    public ControlModelImpl(ControlPresenterImpl presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getFixture(int id) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getFixtureDao()
                .getFixtureInfoFromId(id)
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
    public void getFixtureGroup(int id) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getFixtureGroupDao()
                .getFixtureGroupInfoFromId(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<FixtureGroup>>() {
                    @Override
                    public void accept(List<FixtureGroup> fixtureGroups) throws Exception {
                        presenter.receiveFixtureGroupList(fixtureGroups);
                    }
                });
    }
}
