package com.example.nanlinkdemo.mvp.widget;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.nanlinkdemo.Application.MyApplication;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.FeasyDevice;
import com.example.nanlinkdemo.databinding.ActivityScanbleBinding;


import java.util.ArrayList;
import java.util.Arrays;



public class ScanBleActivity extends AppCompatActivity {

    ArrayList<byte[]> uuidlist = new ArrayList<byte[]>();
    ArrayList<FeasyDevice> deviceList = new ArrayList<FeasyDevice>();


    private ActivityScanbleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullScreen(this);
        binding = ActivityScanbleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.Scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
                if (bluetoothManager == null) {
                    return;
                }
                BluetoothAdapter adapter = bluetoothManager.getAdapter();
                if (adapter == null) {
                    return;
                } else if (!adapter.isEnabled()) {
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, 1);
                } else {
                    BluetoothLeScanner scanner = adapter.getBluetoothLeScanner();
                    if (ActivityCompat.checkSelfPermission(ScanBleActivity.this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        ActivityCompat.requestPermissions(ScanBleActivity.this, new String[]{Manifest.permission.BLUETOOTH_SCAN}, 1);
//                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.BLUETOOTH_SCAN)){
//                    Log.d("TAG", "onViewClicked: true" );
//                }else {
//                    Log.d("TAG", "onViewClicked: false" );
//                }
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.

                    }
                    ScanCallback callback = new ScanCallback() {
                        @Override
                        public void onScanResult(int callbackType, ScanResult result) {
                            super.onScanResult(callbackType, result);
                            if (result.getScanRecord().getServiceData() != null & result.getScanRecord().getServiceUuids() != null){
                                Log.d("TAG", "onScanResult: " + result);
                                byte[] uuid =  result.getScanRecord().getServiceData().get(result.getScanRecord().getServiceUuids().get(0));
                                if (uuid[14] != 78){
                                    return;
                                }
                                StringBuilder sb = new StringBuilder();
                                byte checknum = 0;
                                for (int i = 6; i < 15; i++) {
                                    checknum = (byte) (checknum + uuid[i]);
                                }
                                if (checknum != uuid[15]){
                                    return;
                                }
                                if (uuidlist.size() != 0){
                                    for (int i = 0; i < uuidlist.size(); i++){
                                        if (Arrays.equals(uuidlist.get(i), uuid)){
                                            return;
                                        }
                                        if (Arrays.equals(Arrays.copyOfRange(uuid, 0, 5), Arrays.copyOfRange(uuidlist.get(i), 0, 5))){
                                            uuidlist.set(i, uuid);
                                            deviceList.set(i, new FeasyDevice(uuid));
                                            Log.d("TAG", "onScanset: " + i);
                                            break;
                                        }
                                        if (i == uuidlist.size() - 1){
                                            uuidlist.add(uuid);
                                            deviceList.add(new FeasyDevice(uuid));
                                        }
                                    }
                                }else {
                                    uuidlist.add(uuid);
                                    Log.d("TAG", "onScanadd: " );

                                }
//                        for (byte[] Uuid: uuidlist){
//                            StringBuilder stringBuilder = new StringBuilder();
//                            for (byte b : Uuid){
//                                stringBuilder.append(String.format("%02X", b));
//                            }
//                        }

//                    Log.d("TAG", "onScanServiceData: " + result.getScanRecord().getDeviceName());
//                        Log.d("TAG", "onScanServiceData: " + result.getScanRecord().getServiceData());



                            }
                            Log.d("TAG", "onViewClicked: " + uuidlist.size());

                        }

                    };
                    scanner.startScan(callback);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (ActivityCompat.checkSelfPermission(MyApplication.getInstance(), Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
//                        return;
                            }
                            scanner.stopScan(callback);
                            Log.d("TAG", "onstopScan: ");

                        }
                    },20000);
                }

            }
        });


    }

    private void fullScreen(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

}