package com.example.nanlinkdemo.mvp.widget;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.nanlinkdemo.R;
import com.example.nanlinkdemo.bean.FeasyDevice;
import com.example.nanlinkdemo.databinding.ActivityRecycleviewMarginTopBinding;
import com.example.nanlinkdemo.mvp.adapter.ScanBleAdapter;
import com.example.nanlinkdemo.mvp.presenter.Impl.ScanBlePresenterImpl;
import com.example.nanlinkdemo.mvp.view.ScanBleView;
import com.example.nanlinkdemo.util.Constant;


import java.util.ArrayList;
import java.util.Arrays;


@Route(path = Constant.ACTIVITY_URL_ScanBle)
public class ScanBleActivity extends BaseActivity<ActivityRecycleviewMarginTopBinding> implements ScanBleView, View.OnClickListener{


    private ScanBleAdapter adapter;
    private ScanBlePresenterImpl presenter;

    ArrayList<byte[]> uuidlist = new ArrayList<byte[]>();
    ArrayList<FeasyDevice> deviceList = new ArrayList<FeasyDevice>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        initToolbar();
        initRecyclerView();
        initBtn();
        initPermission();
        initBle();



    }

    private void initPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
//            return;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions(ScanBleActivity.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 1);
            }

        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
//            return;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions(ScanBleActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }

        }

    }



    private void initBle() {

        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter adapter = bluetoothManager.getAdapter();
        if (!adapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, 1);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
//            return;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions(ScanBleActivity.this, new String[]{Manifest.permission.BLUETOOTH_SCAN}, 1);
            }

        }

        BluetoothLeScanner scanner = adapter.getBluetoothLeScanner();
        ScanCallback scanCallback = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);

                if (result.getScanRecord().getServiceData() != null & result.getScanRecord().getServiceUuids() != null) {
                    byte[] uuid = result.getScanRecord().getServiceData().get(result.getScanRecord().getServiceUuids().get(0));
                    if (uuid[14] != 78) {
                        return;
                    }
                    StringBuilder sb = new StringBuilder();
                    byte checknum = 0;
                    for (int i = 6; i < 15; i++) {
                        checknum = (byte) (checknum + uuid[i]);
                    }
                    if (checknum != uuid[15]) {
                        return;
                    }
                    if (uuidlist.size() != 0) {
                        for (int i = 0; i < uuidlist.size(); i++) {
                            if (Arrays.equals(uuidlist.get(i), uuid)) {
                                return;
                            }
                            if (Arrays.equals(Arrays.copyOfRange(uuid, 0, 5), Arrays.copyOfRange(uuidlist.get(i), 0, 5))) {
                                uuidlist.set(i, uuid);
                                deviceList.set(i, new FeasyDevice(uuid));
                                showBle(deviceList);

                                break;
                            }
                            if (i == uuidlist.size() - 1) {
                                uuidlist.add(uuid);
                                deviceList.add(new FeasyDevice(uuid));
                                showBle(deviceList);
                            }
                        }
                    } else {
                        uuidlist.add(uuid);
                        deviceList.add(new FeasyDevice(uuid));
                        showBle(deviceList);

                    }


                }
            }
        };
        scanner.startScan(scanCallback);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ActivityCompat.checkSelfPermission(ScanBleActivity.this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
//                    return;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        ActivityCompat.requestPermissions(ScanBleActivity.this, new String[]{Manifest.permission.BLUETOOTH_SCAN}, 1);
                    }
                }
                scanner.stopScan(scanCallback);
//                showBle(deviceList);

                Log.d("TAG", "onStopScan:");

            }
        }, 20000);

    }

    private void initBtn() {
        binding.twoBtn.setVisibility(View.VISIBLE);
        binding.allSelected.setOnClickListener(this);
        binding.complete.setOnClickListener(this);
    }

    private void initToolbar() {
        binding.toolbar.setTitle("通过蓝牙");
        binding.toolbar.setLeftBtnIcon(R.drawable.ic_back);
        binding.toolbar.setLeftBtnOnClickListener(this);
        binding.toolbar.setRightBtnIcon(R.drawable.ic_rescan);
        binding.toolbar.setRightBtnOnClickListener(this);
    }

    @Override
    public void setPresenter() {
        presenter = new ScanBlePresenterImpl(this);
    }

    @Override
    public void showBle(ArrayList<FeasyDevice> arrayList) {
        adapter.setData(arrayList);

    }

    private void initRecyclerView() {
        binding.recycleView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        adapter = new ScanBleAdapter();
        presenter.getListDataFromView();
        binding.recycleView.setAdapter(adapter);
        adapter.setOnClickListener(new ScanBleAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                presenter.onClickSwitch(position);
            }
        });
    }


    @Override
    public void onClick(View view) {
        presenter.onClickSwitch(view);

    }
}