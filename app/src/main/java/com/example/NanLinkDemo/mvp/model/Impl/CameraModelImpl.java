package com.example.NanLinkDemo.mvp.model.Impl;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.DataBase.MyDataBase;
import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.FixtureGroup;
import com.example.NanLinkDemo.mvp.model.CameraModel;
import com.example.NanLinkDemo.mvp.presenter.Impl.CameraPresenterImpl;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CameraModelImpl implements CameraModel {
    private final CameraPresenterImpl presenter;

    public CameraModelImpl(CameraPresenterImpl presenter) {
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
