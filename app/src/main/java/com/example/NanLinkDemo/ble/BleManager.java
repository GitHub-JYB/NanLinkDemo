package com.example.NanLinkDemo.ble;

import static com.example.NanLinkDemo.util.Constant.PERMISSION_REQUEST_CODE;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;
import java.util.logging.LogRecord;


public class BleManager {

    private static BleManager bleManager;
    private static BluetoothLeScanner scanner;
    private final ScanSettings scanSettings;
    private final Context context;
    private final MutableLiveData<Boolean> isScanning = new MutableLiveData<>();

    private ArrayList<String> permissionList;
    private ScanCallback callback;


    private BleManager(Context context) {
        this.context = context;
        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        scanner = bluetoothAdapter.getBluetoothLeScanner();
        ScanSettings.Builder builder = new ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            builder.setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES);
            builder.setMatchMode(ScanSettings.MATCH_MODE_STICKY);
        }
        if (bluetoothAdapter.isOffloadedScanBatchingSupported()) {
            builder.setReportDelay(0L);
        }
        scanSettings = builder.build();
        isScanning.postValue(false);
    }

    public static BleManager getScanner(Context context) {
        if (scanner == null) {
            bleManager = new BleManager(context);
        }
        return bleManager;
    }


    public void startScan(ScanCallback callback) {
        if (checkPermission()) {
            if (Boolean.FALSE.equals(isScanning.getValue())) {
                isScanning.postValue(true);
                Log.i("test", "startScan: ");
                scanner.startScan(null, scanSettings, callback);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        stopScan();
                    }
                }, 12000);

            }
        }
    }

    public void stopScan() {
        if (checkPermission()) {
            if (Boolean.TRUE.equals(isScanning.getValue())) {
                scanner.stopScan(callback);
                isScanning.postValue(false);
                Log.i("test", "stopScan: ");
            }
        }
    }

    private boolean checkPermission() {
        permissionList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.BLUETOOTH_SCAN);
            }
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_ADVERTISE) != PackageManager.PERMISSION_GRANTED) {

                permissionList.add(Manifest.permission.BLUETOOTH_ADVERTISE);

            }
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

                permissionList.add(Manifest.permission.BLUETOOTH_CONNECT);

            }
        }
        if (permissionList.isEmpty()) {
            return true;
        } else {
            ActivityCompat.requestPermissions((Activity) context, permissionList.toArray(new String[]{}), PERMISSION_REQUEST_CODE);
            return false;
        }
    }

    public LiveData<Boolean> isScanning() {
        return isScanning;
    }
}
