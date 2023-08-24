package com.example.NanLinkDemo.service;


import android.Manifest;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.core.app.ActivityCompat;


import java.util.ArrayList;

import no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat;
import no.nordicsemi.android.support.v18.scanner.ScanCallback;
import no.nordicsemi.android.support.v18.scanner.ScanResult;
import no.nordicsemi.android.support.v18.scanner.ScanSettings;

public class BleScanService extends Service {

    public static final String resultAction = "BLEScan_Result";
    public static final String scanStateAction = "BLEScan_scanState";
    private BluetoothLeScannerCompat scanner;
    private ScanCallback scanCallback;
    private boolean isScanning = false;
    private ScanSettings scanSettings;

    public BleScanService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
        scanner = BluetoothLeScannerCompat.getScanner();
        scanCallback = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                Intent intent = new Intent();
                intent.putExtra("result", result);
                intent.setAction(resultAction);
                sendBroadcast(intent);
            }
        };

        scanSettings = new ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
                .setMatchMode(ScanSettings.MATCH_MODE_STICKY)
                .setReportDelay(0L)
                .build();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent intent1 = new Intent();
        if (checkPermission() && scanner != null) {
            if (!isScanning) {
                isScanning = true;
                intent1.putExtra("isScanning", true);
                intent1.setAction(scanStateAction);
                sendBroadcast(intent1);
                scanner.startScan(null, scanSettings, scanCallback);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isScanning = false;
                        intent1.putExtra("isScanning", false);
                        intent1.setAction(scanStateAction);
                        sendBroadcast(intent1);
                        onDestroy();
                    }
                }, 12000);
            } else {
                intent1.putExtra("isScanning", true);
                intent1.setAction(scanStateAction);
                sendBroadcast(intent1);
            }

        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        if (checkPermission()) {
            scanner.stopScan(scanCallback);
        }
        super.onDestroy();
    }

    private boolean checkPermission() {
        ArrayList<String> permissionList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.BLUETOOTH_SCAN);
            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADVERTISE) != PackageManager.PERMISSION_GRANTED) {

                permissionList.add(Manifest.permission.BLUETOOTH_ADVERTISE);

            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

                permissionList.add(Manifest.permission.BLUETOOTH_CONNECT);

            }
        }
        return permissionList.isEmpty();
    }
}