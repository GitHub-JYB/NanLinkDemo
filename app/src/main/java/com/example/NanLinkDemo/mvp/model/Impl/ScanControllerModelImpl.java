package com.example.NanLinkDemo.mvp.model.Impl;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.DataBase.MyDataBase;
import com.example.NanLinkDemo.DB.bean.Controller;
import com.example.NanLinkDemo.bean.FeasyDevice;
import com.example.NanLinkDemo.mvp.model.ScanControllerModel;
import com.example.NanLinkDemo.mvp.presenter.Impl.ScanControllerPresenterImpl;

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
