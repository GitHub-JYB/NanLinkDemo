package com.example.nanlinkdemo.mvp.model.Impl;

import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.DB.DataBase.MyDataBase;
import com.example.nanlinkdemo.DB.bean.Controller;
import com.example.nanlinkdemo.DB.bean.Fixture;
import com.example.nanlinkdemo.bean.FeasyDevice;
import com.example.nanlinkdemo.mvp.model.ScanControllerModel;
import com.example.nanlinkdemo.mvp.presenter.Impl.ScanControllerPresenterImpl;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ScanControllerModelImpl implements ScanControllerModel {
    private final ScanControllerPresenterImpl presenter;

    public ScanControllerModelImpl(ScanControllerPresenterImpl presenter) {
        this.presenter = presenter;
    }

    @Override
    public void addController(FeasyDevice device) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getControllerDao()
                .insert(new Controller(MyApplication.getOnlineUser().getEmail(), device.getNAME(), device.getUUID(),device.getDEVICE_ID(), "蓝牙"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                    }
                });
    }
}
