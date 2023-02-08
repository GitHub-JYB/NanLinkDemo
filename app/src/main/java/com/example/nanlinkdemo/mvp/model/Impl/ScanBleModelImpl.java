package com.example.nanlinkdemo.mvp.model.Impl;

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

import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.bean.FeasyDevice;
import com.example.nanlinkdemo.mvp.model.ScanBleModel;
import com.example.nanlinkdemo.mvp.presenter.Impl.ScanBlePresenterImpl;
import com.example.nanlinkdemo.mvp.widget.ScanBleActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class ScanBleModelImpl implements ScanBleModel {
    private final ScanBlePresenterImpl presenter;



    public ScanBleModelImpl(ScanBlePresenterImpl presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getListData() {



    }
}
