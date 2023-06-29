package com.example.NanLinkDemo.mvp.model.Impl;

import static android.content.Context.BLUETOOTH_SERVICE;


import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.DB.DataBase.MyDataBase;
import com.example.NanLinkDemo.DB.bean.Fixture;
import com.example.NanLinkDemo.DB.bean.Scene;
import com.example.NanLinkDemo.bean.FeasyDevice;
import com.example.NanLinkDemo.mvp.model.ScanBleModel;
import com.example.NanLinkDemo.mvp.presenter.Impl.ScanBlePresenterImpl;
import com.example.NanLinkDemo.mvp.widget.ScanBleActivity;

import java.util.ArrayList;
import java.util.Arrays;

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
    public void addBleFixture(FeasyDevice device) {
        Disposable disposable = MyDataBase.getInstance(MyApplication.getInstance())
                .getFixtureDao()
                .insert(new Fixture(MyApplication.getOnlineUser().getEmail(), MyApplication.getScene().getName(), device.getNAME(), device.getCH(),device.getDEVICE_ID(), "蓝牙", ""))
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
