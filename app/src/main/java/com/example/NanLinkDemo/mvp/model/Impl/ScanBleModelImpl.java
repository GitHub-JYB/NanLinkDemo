package com.example.NanLinkDemo.mvp.model.Impl;


import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.DataBase.MyDataBase;
import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.Scene;
import com.example.NanLinkDemo.bean.Device;
import com.example.NanLinkDemo.mvp.model.ScanBleModel;
import com.example.NanLinkDemo.mvp.presenter.Impl.ScanBlePresenterImpl;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ScanBleModelImpl implements ScanBleModel {
    private final ScanBlePresenterImpl presenter;



    public ScanBleModelImpl(ScanBlePresenterImpl presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getListData() {

    }

    @Override
    public void addBleFixture(Fixture fixture) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getFixtureDao()
                .insert(fixture)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        presenter.updateScene();
                    }
                });
    }

    @Override
    public void updateScene(Scene scene) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getSceneDao()
                .updateSceneInfo(scene)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {

                    }
                });
    }
}
