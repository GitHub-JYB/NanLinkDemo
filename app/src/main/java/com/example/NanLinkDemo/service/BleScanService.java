package com.example.NanLinkDemo.service;

import static com.example.NanLinkDemo.util.Constant.PERMISSION_REQUEST_CODE;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.example.NanLinkDemo.bean.Device;

import java.util.ArrayList;

public class BleScanService extends Service {

    public static final String resultAction = "BLEScan_Result";
    public static final String scanStateAction = "BLEScan_scanState";
    private BluetoothLeScanner scanner;
    private ScanCallback scanCallback;
    private boolean isScanning = false;

    public BleScanService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        scanner = bluetoothAdapter.getBluetoothLeScanner();
        scanCallback = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                Intent intent = new Intent();
                intent.putExtra("result", result);
                intent.setAction(resultAction);
                sendBroadcast(intent);
            }
        };
        ScanSettings.Builder builder = new ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            builder.setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES);
            builder.setMatchMode(ScanSettings.MATCH_MODE_STICKY);
        }
        if (bluetoothAdapter.isOffloadedScanBatchingSupported()) {
            builder.setReportDelay(0L);
        }
        ScanSettings scanSettings = builder.build();
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
                scanner.startScan(scanCallback);
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